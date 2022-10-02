package com.smartinventory.exceptions.user;

import com.smartinventory.appuser.AppUser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameTakenException extends RuntimeException {
    
    public UsernameTakenException(AppUser user) {
        super(String.format("username %s already taken!", user));
    }
}
