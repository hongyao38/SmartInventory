package com.smartinventory.auth;

import org.springframework.stereotype.Service;
import com.smartinventory.user.*;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    public String register(AuthRequest request) {
        return userService.registerUser(
            new User(request.getEmail(), request.getUsername(), request.getPassword())
        );
    }

    public User forgetUserPassword(AuthRequest request) {
        return userService.forgetUserPassword(
            new User(request.getEmail(), request.getUsername(), request.getPassword()));
    }
    
}
