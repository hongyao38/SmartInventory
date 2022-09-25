package com.smartinventory.user;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User("me@live.com.sg", "Bobby", "1234"));
    }

    public List<User> listUsers() {
        return users;
    }
}
