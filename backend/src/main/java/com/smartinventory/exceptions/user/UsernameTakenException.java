package com.smartinventory.exceptions.user;

import com.smartinventory.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameTakenException extends RuntimeException {
    
    public UsernameTakenException(User user) {
        super(String.format("username %s already taken!", user));
    }
}
