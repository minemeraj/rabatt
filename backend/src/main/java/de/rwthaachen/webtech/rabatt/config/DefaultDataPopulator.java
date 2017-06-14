package de.rwthaachen.webtech.rabatt.config;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.rwthaachen.webtech.rabatt.model.Discount;
import de.rwthaachen.webtech.rabatt.model.User;
import de.rwthaachen.webtech.rabatt.repository.DiscountRepository;
import de.rwthaachen.webtech.rabatt.repository.UserRepository;

@Component
public class DefaultDataPopulator {

  @Autowired
  UserRepository userRepository;

  @Autowired
  DiscountRepository discountRepository;

  @PostConstruct
  @Transactional
  public void init() {
    createUsers();
    createDiscounts();
  }

  private void createDiscounts() {
    List<User> users = userRepository.findAll();
    Date today = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(today);
    c.add(Calendar.DATE, 1);
    Date tomorrow = c.getTime();

    Discount netto = new Discount();
    netto.setTitle("Half price coca-cola");
    netto.setValidFrom(today);
    netto.setValidFrom(tomorrow);
    netto.setCreator(users.get(0));

    if (discountRepository.findByTitle(netto.getTitle()).isEmpty()) {
      discountRepository.save(netto);
    }

    Discount kfc = new Discount();
    kfc.setTitle("20% discount Chicken Wing");
    kfc.setValidFrom(today);
    kfc.setValidFrom(tomorrow);
    kfc.setCreator(users.get(1));

    if (discountRepository.findByTitle(kfc.getTitle()).isEmpty()) {
      discountRepository.save(kfc);
    }
  }

  private void createUsers() {
    Set<String> permission = new HashSet<>();
    permission.add(AuthoritiesConstants.ADMIN);
    permission.add(AuthoritiesConstants.USER);

    User fabrice = new User();
    fabrice.setUsername("fabrice");
    fabrice.setPassword("fab123");
    fabrice.setEmail("fabrice@gmail.com");
    fabrice.setRole(AuthoritiesConstants.ADMIN);
    fabrice.setEnabled(true);

    if (userRepository.findByUsername("fabrice").isEmpty()) {
      userRepository.save(fabrice);
    }

    User paulson = new User();
    paulson.setUsername("paulson");
    paulson.setPassword("bond");
    paulson.setEmail("paulson@gmail.com");
    paulson.setRole(AuthoritiesConstants.ADMIN);
    paulson.setEnabled(true);

    if (userRepository.findByUsername("paulson").isEmpty()) {
      userRepository.save(paulson);
    }
  }
}
