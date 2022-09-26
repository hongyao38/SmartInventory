package com.smartinventory.user;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
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
