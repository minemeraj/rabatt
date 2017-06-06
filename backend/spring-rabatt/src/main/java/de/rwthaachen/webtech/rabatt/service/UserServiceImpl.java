package de.rwthaachen.webtech.rabatt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.rwthaachen.webtech.rabatt.model.User;
import de.rwthaachen.webtech.rabatt.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    UserRepository repository;

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        repository.delete(id);
    }

    @Override
    public boolean isUserExist(User user) {
        if (user.getId() == null) {
            return false;
        }
        return findById(user.getId()) != null;
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        repository.deleteAll();
    }

}
