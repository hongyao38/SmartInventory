package com.smartinventory.user;

import java.util.*;

public interface UserService {
    List<User> listUsers();
    User getUser(String email);
    String getEmail(String username);
    User addUser(String email, String username, String password);
    User updateUser(String email, String password);
    void deleteUser(String email);

}
