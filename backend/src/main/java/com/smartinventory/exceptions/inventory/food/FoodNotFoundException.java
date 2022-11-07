package com.smartinventory.exceptions.inventory.food;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FoodNotFoundException extends RuntimeException {

    public FoodNotFoundException() {
        super(String.format("Food is not found!"));
    }
    
    public FoodNotFoundException(String foodName) {
        super(String.format("%s not found!", foodName));
    }

}