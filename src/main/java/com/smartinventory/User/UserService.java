package com.smartinventory.user;

import java.util.*;

public interface UserService {
    List<User> listUsers();
    User getUser(String email);
    String getEmail(String username);
    User addUser(User user);
    User updateUser(String email, User user);
    void deleteUser(String email);
    
}
