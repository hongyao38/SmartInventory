package com.smartinventory.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContainerExistsException extends RuntimeException {

    public ContainerExistsException() {
        super(String.format("Container exists!"));
    }

}
