package com.smartinventory.user;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository users;

    public UserServiceImpl(UserRepository users) {
        this.users = users;
    }

    @Override
    public List<User> listUsers() {
        return users.findAll();
    }

    @Override
    public User getUser(String email) {
        return users.findById(email).orElse(null);
    }

    @Override
    public String getEmail(String username) {
        return users.findEmailByUsername(username);
    }

    @Override
    public User addUser(User user) {
        if (getUser(user.getEmail()) == null) {
            return users.save(user);
        } else {
            return null;
        }
    }

    @Override
    public User updateUser(String email, User newUser) {
        return users.findById(email).map(user -> {user.setPassword(newUser.getPassword());
            return users.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(String email) {
        users.deleteById(email);
    }

    
}
