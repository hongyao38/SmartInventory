package com.smartinventory.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidConsumptionException extends RuntimeException {
    
    public InvalidConsumptionException(String message) {
        super(String.format(message));
    }

}
