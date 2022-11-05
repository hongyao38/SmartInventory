package com.smartinventory.exceptions.inventory.consumption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidConsumptionException extends RuntimeException {
    
    public InvalidConsumptionException(String message) {
        super(String.format(message));
    }

}
