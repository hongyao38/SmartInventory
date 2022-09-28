package com.smartinventory.user;

import java.util.*;

public interface UserService {
    List<User> listUsers();
    User getUserByEmail(String email);
    String getEmail(String username);
    User addUser(User user);
    User updateUserPassword(String email, String password);
    void deleteUser(String email);
}
