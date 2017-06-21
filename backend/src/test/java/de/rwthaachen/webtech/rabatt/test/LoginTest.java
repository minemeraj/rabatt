package de.rwthaachen.webtech.rabatt.test;

import javax.servlet.ServletException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.rwthaachen.webtech.rabatt.config.AuthoritiesConstants;
import de.rwthaachen.webtech.rabatt.config.DispatcherServletInitializer;
import de.rwthaachen.webtech.rabatt.config.WebConfig;
import de.rwthaachen.webtech.rabatt.config.WebSecurityConfig;
import de.rwthaachen.webtech.rabatt.controller.AuthController;
import de.rwthaachen.webtech.rabatt.model.User;
import de.rwthaachen.webtech.rabatt.repository.UserRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {WebConfig.class, WebSecurityConfig.class, DispatcherServletInitializer.class})
public class LoginTest extends JPAHibernateTest {

  @Autowired
  private AuthController authController;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void login() {
    User user = createUser();
    ResponseEntity<?> response = authController.login(user);
    Assert.assertThat(response.getBody().toString(), CoreMatchers.containsString("token"));
  }

  @Test
  public void currentUser() {
    User user = createUser();
    ResponseEntity<?> response = authController.login(user);
    String token = response.getBody().toString();
    token = token.substring(7);
    token = token.substring(0, token.length() - 1);
    try {
      response = authController.currentUser("Bearer " + token);
      Assert.assertThat(response.getBody().toString(), CoreMatchers.containsString("testUser"));
    } catch (ServletException e) {
      e.printStackTrace();
    }
  }

  private User createUser() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setPassword("testUser");
    testUser.setEmail("testUser@gmail.com");
    testUser.setRole(AuthoritiesConstants.ADMIN);
    testUser.setEnabled(true);
    userRepository.save(testUser);
    return testUser;
  }
}
