package com.smartinventory.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEmailTakenException extends RuntimeException {
    
    public UserEmailTakenException() {
        super("Email already taken");
    }

}
