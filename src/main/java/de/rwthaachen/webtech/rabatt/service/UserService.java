package de.rwthaachen.webtech.rabatt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import de.rwthaachen.webtech.rabatt.model.User;

public interface UserService extends UserDetailsService {

	void saveUser(User user, String password);

	void saveUser(User user);

}
