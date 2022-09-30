package com.smartinventory.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIdNotFoundException extends RuntimeException {
    
    public UserIdNotFoundException(Long id) {
        super(String.format("User with ID: %d not found", id));
    }

}
