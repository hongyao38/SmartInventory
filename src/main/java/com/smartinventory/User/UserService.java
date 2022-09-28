package com.smartinventory.user;

import java.util.*;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    List<User> listUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    String getEmail(String username);
    User addUser(User user);
    User updateUserPassword(String email, String password);
    void deleteUser(String email);
}
