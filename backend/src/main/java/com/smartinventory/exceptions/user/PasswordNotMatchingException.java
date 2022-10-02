package com.smartinventory.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordNotMatchingException extends RuntimeException {
    
    public PasswordNotMatchingException() {
        super("Passwords entered are not matching!");
    }

}
