package de.rwthaachen.webtech.rabatt.service;

import java.util.List;

import de.rwthaachen.webtech.rabatt.model.User;



public interface UserService {
	
	User findById(Integer id);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(Integer id);

	List<User> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(User user);
	
}
