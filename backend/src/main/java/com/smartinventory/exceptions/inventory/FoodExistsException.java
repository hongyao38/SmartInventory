package com.smartinventory.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FoodExistsException extends RuntimeException {
    
    public FoodExistsException(String foodName) {
        super(String.format("%s not found!", foodName));
    }

}
