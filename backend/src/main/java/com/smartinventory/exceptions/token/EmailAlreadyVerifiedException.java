package com.smartinventory.exceptions.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyVerifiedException extends RuntimeException {

    public EmailAlreadyVerifiedException() {
        super("Email already verified!");
    }
    
}
