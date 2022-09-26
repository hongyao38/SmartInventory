package com.smartinventory.user;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findById(email).orElse(null);
    }

    @Override
    public String getEmail(String username) {
        return userRepository.findEmailByUsername(username);
    }

    @Override
    public User updateUser(String email, User newUser) {
        return userRepository.findById(email).map(user -> {user.setPassword(newUser.getPassword());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteById(email);
    }

    /*
     * Create new exceptions to throw for cases 
     * where users are not added successfully
     * Update UserServiceImpl accordingly
     */
    public User addUser(User user) {
        if (!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            return null;
        }
        return userRepository.save(user);
    }
}
