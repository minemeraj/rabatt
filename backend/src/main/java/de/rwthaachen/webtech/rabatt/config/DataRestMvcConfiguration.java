package de.rwthaachen.webtech.rabatt.config;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import de.rwthaachen.webtech.rabatt.model.Discount;
import de.rwthaachen.webtech.rabatt.model.User;

@Component
public class DataRestMvcConfiguration extends RepositoryRestConfigurerAdapter {

  public static final String DATA_REST_BASEPATH = "spring.data.rest.basePath";
  @Resource
  private Environment env;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.setBasePath(env.getProperty(DATA_REST_BASEPATH));
    config.exposeIdsFor(User.class);
    config.exposeIdsFor(Discount.class);
    // config.setDefaultMediaType(new MediaType(MediaTypes.JSON_VALUE));
  }
}
