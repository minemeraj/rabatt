package de.rwthaachen.webtech.rabatt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.rwthaachen.webtech.rabatt.model.User;
import java.lang.String;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByUsername(String username);

}
