package com.smartinventory.user;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    private List<User> users = new ArrayList<>();

    public UserServiceImpl() {
        users.add(new User("me@live.com.sg", "Bobby", "1234"));
    }

    public List<User> listUsers() {
        return users;
    }

    
}
