package com.smartinventory.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIdNotFoundException extends RuntimeException {
    
    public UserIdNotFoundException(String id) {
        super(String.format("User with %s not found", id));
    }

}
