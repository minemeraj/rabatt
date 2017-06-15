package de.rwthaachen.webtech.rabatt.config;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
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
    ClassLoader classLoader = getClass().getClassLoader();

    Discount netto = new Discount();
    netto.setTitle("Half price coca-cola");
    netto.setValidFrom(today);
    netto.setValidUntil(tomorrow);
    netto.setCategory("Food");
    netto.setLink("http://google.com");
    netto.setCreator(users.get(0));
    netto.setDescription(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam rhoncus dignissim lobortis. Nam vel mauris nibh. Aliquam fringilla nibh lectus, id venenatis justo viverra sed. Phasellus ultricies ornare malesuada. In tincidunt ex in tincidunt commodo. Vestibulum at leo a ex tempor posuere. Vivamus ullamcorper nec turpis non porttitor. Curabitur tempus libero ac nulla imperdiet placerat. Donec non fermentum justo. Fusce turpis odio, rutrum eu dignissim sed, accumsan ac ante. Praesent ut quam nec orci convallis mattis eget finibus ipsum. Maecenas id ante faucibus, sagittis mi eget, tempus turpis. Pellentesque sollicitudin sit amet dui quis vehicula. Suspendisse vitae posuere lectus");
    try {
      @SuppressWarnings("deprecation")
      String result = IOUtils.toString(classLoader.getResourceAsStream("image1.txt"));
      netto.setImage(result);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (discountRepository.findByTitle(netto.getTitle()).isEmpty()) {
      discountRepository.save(netto);
    }

    Discount kfc = new Discount();
    kfc.setTitle("20% discount Chicken Wing");
    kfc.setValidFrom(today);
    kfc.setValidUntil(tomorrow);
    kfc.setCategory("Food");
    kfc.setLink("http://facebook.com");
    kfc.setCreator(users.get(1));
    kfc.setDescription(
        "Nulla vulputate sem purus, ornare porta augue commodo nec. Sed vitae est est. Pellentesque feugiat gravida arcu, volutpat lacinia libero congue fermentum. Nunc lobortis finibus feugiat. Donec lobortis ipsum id scelerisque maximus. Curabitur sagittis sed eros sed varius. Sed vel convallis est. Nunc rutrum leo vitae nisl cursus tempus. Pellentesque porta lacinia dolor sit amet facilisis. Donec placerat nec orci sed eleifend.");
    try {
      @SuppressWarnings("deprecation")
      String result = IOUtils.toString(classLoader.getResourceAsStream("image2.txt"));
      kfc.setImage(result);
    } catch (IOException e) {
      e.printStackTrace();
    }

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
