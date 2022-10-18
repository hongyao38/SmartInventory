package com.smartinventory.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContainerNotFoundException extends RuntimeException {

    public ContainerNotFoundException() {
        super(String.format("Container is not found!"));
    }

}
