package de.rwthaachen.webtech.rabatt.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.rwthaachen.webtech.rabatt.model.User;
import de.rwthaachen.webtech.rabatt.repository.UserRepository;

@Component
public class DefaultDataPopulator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder shaPasswordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        // creating dummy user
        createUsers();
    }

    private void createUsers() {
        Set<String> permission = new HashSet<>();
        permission.add(AuthoritiesConstants.ADMIN);
        permission.add(AuthoritiesConstants.USER);

        User fabrice = new User();
        fabrice.setUsername("fabrice");
        fabrice.setPassword(shaPasswordEncoder.encode("fab123"));
        fabrice.setEmail("fabrice@gmail.com");
        fabrice.setRole(AuthoritiesConstants.ADMIN);
        fabrice.setEnabled(true);

        if (userRepository.findByUsername("fabrice").isEmpty()) {
            userRepository.save(fabrice);
        }

        User paulson = new User();
        paulson.setUsername("paulson");
        paulson.setPassword(shaPasswordEncoder.encode("bond"));
        paulson.setEmail("paulson@gmail.com");
        paulson.setRole(AuthoritiesConstants.ADMIN);
        paulson.setEnabled(true);

        if (userRepository.findByUsername("paulson").isEmpty()) {
            userRepository.save(paulson);
        }
    }
}
