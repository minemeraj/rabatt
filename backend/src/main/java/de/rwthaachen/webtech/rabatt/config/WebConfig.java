package de.rwthaachen.webtech.rabatt.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Import(RepositoryRestMvcConfiguration.class)
@ComponentScan(basePackages = "de.rwthaachen.webtech.rabatt")
@EnableJpaRepositories("de.rwthaachen.webtech.rabatt.repository")
public class WebConfig extends WebMvcConfigurerAdapter {

  @Resource
  private Environment env;

  private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
  private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
  private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
  private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

  private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
  private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
  private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

  private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN =
      "entitymanager.packages.to.scan";

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/")
        .resourceChain(false).addResolver(new WebJarsResourceResolver())
        .addResolver(new PathResourceResolver());
  }



  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
    dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
    dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
    dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean
        .setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

    entityManagerFactoryBean.setJpaProperties(hibProperties());

    return entityManagerFactoryBean;
  }

  private Properties hibProperties() {
    Properties properties = new Properties();
    properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,
        env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
    properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
        env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
    properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
        env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename(env.getRequiredProperty("message.source.basename"));
    source.setUseCodeAsDefaultMessage(true);
    return source;
  }
}
