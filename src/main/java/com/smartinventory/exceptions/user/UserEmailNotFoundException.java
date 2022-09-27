package com.smartinventory.exceptions.user;

public class UserEmailNotFoundException extends RuntimeException {
    
    public UserEmailNotFoundException(String email) {
        super(String.format("user with email %s not found!", email));
    }

}
