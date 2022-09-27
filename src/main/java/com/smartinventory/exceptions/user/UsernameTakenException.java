package com.smartinventory.exceptions.user;

public class UsernameTakenException extends RuntimeException {
    
    public UsernameTakenException(String username) {
        super(String.format("username %s already taken!", username));
    }
}
