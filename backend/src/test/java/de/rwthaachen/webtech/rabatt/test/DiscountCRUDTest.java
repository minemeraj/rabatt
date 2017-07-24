package de.rwthaachen.webtech.rabatt.test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.rwthaachen.webtech.rabatt.config.AuthoritiesConstants;
import de.rwthaachen.webtech.rabatt.config.DispatcherServletInitializer;
import de.rwthaachen.webtech.rabatt.config.WebConfig;
import de.rwthaachen.webtech.rabatt.config.WebSecurityConfig;
import de.rwthaachen.webtech.rabatt.model.Discount;
import de.rwthaachen.webtech.rabatt.model.User;
import de.rwthaachen.webtech.rabatt.repository.DiscountRepository;
import de.rwthaachen.webtech.rabatt.repository.UserRepository;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(
    classes = {WebConfig.class, WebSecurityConfig.class, DispatcherServletInitializer.class})
public class DiscountCRUDTest extends JPAHibernateTest {
  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void findByCreator() {
    create();
    User user = userRepository.findAll().get(0);
    Discount discount = discountRepository.findByCreator(user).get(0);
    assertNotNull(null);
  }

  @Test
  public void findByTitle() {
    create();
    Discount discount = discountRepository.findByTitle("Half price coca-cola").get(0);
    assertNotNull(discount);
  }

  @Test
  public void findAll() {
    create();
    List<Discount> discounts = discountRepository.findAll();
    assertEquals(0, discounts.size());
  }

  @Test
  public void create() {
    Date today = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(today);
    c.add(Calendar.DATE, 1);
    Date tomorrow = c.getTime();

    User testUser = new User();
    testUser.setUsername("testUser");
    testUser.setPassword("testUser");
    testUser.setEmail("testUser@gmail.com");
    testUser.setRole(AuthoritiesConstants.ADMIN);
    testUser.setEnabled(true);
    userRepository.save(testUser);

    Discount netto = new Discount();
    netto.setTitle("Half price coca-cola");
    netto.setValidFrom(today);
    netto.setValidUntil(tomorrow);
    netto.setCategory("Food");
    netto.setLink("http://google.com");
    netto.setCreator(testUser);
    discountRepository.save(netto);

    Discount discount = discountRepository.findByCreator(testUser).get(0);
    assertNotNull(discount);

    List<Discount> discounts = discountRepository.findAll();
    assertEquals(1, discounts.size());
  }

  @Test
  public void update() {
    create();
    Discount discount = discountRepository.findAll().get(0);
    assertEquals("Half price coca-cola", discount.getTitle());
    discount.setTitle("pepsi");

    discountRepository.save(discount);

    discount = discountRepository.findAll().get(0);
    assertEquals("pepsi!", discount.getTitle());
  }

  @Test
  public void delete() {
    create();
    List<Discount> discounts = discountRepository.findAll();
    assertEquals(1, discounts.size());

    discountRepository.deleteAll();

    discounts = discountRepository.findAll();
    assertEquals(0, discounts.size());
  }
}
