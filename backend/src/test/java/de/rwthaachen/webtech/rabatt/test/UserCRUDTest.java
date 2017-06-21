package de.rwthaachen.webtech.rabatt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import de.rwthaachen.webtech.rabatt.config.*;
import de.rwthaachen.webtech.rabatt.model.User;
import de.rwthaachen.webtech.rabatt.repository.UserRepository;

import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {WebConfig.class, WebSecurityConfig.class, DispatcherServletInitializer.class})
public class UserCRUDTest extends JPAHibernateTest {
  @Autowired
  private UserRepository userRepository;

  @Test
  public void findByUsername() {
    create();
    User user = userRepository.findByUsername("testUser").get(0);
    assertNotNull(user);
  }

  @Test
  public void findAll() {
    create();
    List<User> users = userRepository.findAll();
    assertEquals(1, users.size());
  }

  @Test
  public void create() {
    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setPassword("testUser");
    testUser.setEmail("testUser@gmail.com");
    testUser.setRole(AuthoritiesConstants.ADMIN);
    testUser.setEnabled(true);

    userRepository.save(testUser);

    User user = userRepository.findByUsername(testUser.getUsername()).get(0);
    assertNotNull(user);

    List<User> users = userRepository.findAll();
    assertEquals(1, users.size());
  }

  @Test
  public void update() {
    create();
    User user = userRepository.findAll().get(0);
    assertEquals("testUser", user.getUsername());
    user.setUsername("John Doe");

    userRepository.save(user);

    user = userRepository.findAll().get(0);
    assertEquals("John Doe", user.getUsername());
  }

  @Test
  public void delete() {
    create();
    List<User> users = userRepository.findAll();
    assertEquals(1, users.size());

    userRepository.deleteAll();

    users = userRepository.findAll();
    assertEquals(0, users.size());
  }
}
