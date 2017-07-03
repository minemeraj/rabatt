package de.rwthaachen.webtech.rabatt.config;

import java.util.Properties;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.elasticsearch.client.Client;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Import(RepositoryRestMvcConfiguration.class)
@ComponentScan(basePackages = "de.rwthaachen.webtech.rabatt")
@EnableJpaRepositories("de.rwthaachen.webtech.rabatt.repository")
@EnableElasticsearchRepositories(basePackages = "de.rwthaachen.webtech.rabatt.elasticrepository")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Resource
	private Environment env;
	private static DataSource dataSource;
	private static Properties properties;

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		setEnvVariable();
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean
				.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

		entityManagerFactoryBean.setJpaProperties(properties);

		return entityManagerFactoryBean;
	}

	private void setEnvVariable() {
		String activeProfile = env.getActiveProfiles()[0];
		String env = System.getenv("RABATT_ENV");

		if (activeProfile.equals("production") || (env != null && env.equals("production"))) {
			productionEnv();
		} else if (activeProfile.equals("development")) {
			developmentEnv();
		} else if (activeProfile.equals("test")) {
			testEnv();
		}
	}

	private void productionEnv() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("net.sf.log4jdbc.DriverSpy");
		dataSource.setUrl(System.getenv("MYSQL_HOST"));
		dataSource.setUsername(System.getenv("MYSQL_USER"));
		dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
		WebConfig.dataSource = dataSource;

		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "false");
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");
		WebConfig.properties = properties;
	}

	private void developmentEnv() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		String host = System.getenv("MYSQL_HOST");
		String username = System.getenv("MYSQL_USER");
		String password = System.getenv("MYSQL_PASSWORD");

		if (host == null) {
			host = env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL);
		}

		if (username == null) {
			username = env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME);
		}

		if (password == null) {
			password = env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD);
		}

		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(host);

		if (username != null) {
			dataSource.setUsername(username);
		}
		if (password != null) {
			dataSource.setPassword(password);
		}
		WebConfig.dataSource = dataSource;

		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
				env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		WebConfig.properties = properties;
	}

	private void testEnv() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:test");
		WebConfig.dataSource = dataSource;

		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.H2Dialect");
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "create");
		WebConfig.properties = properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}

	@Bean
	public Client client() {
		return EmbeddedElasticsearchServer.getInstance().getClient();
	}

	@PreDestroy
	public void shutdownElasticsearchServer() {
		EmbeddedElasticsearchServer.getInstance().shutdown();
	}

}
