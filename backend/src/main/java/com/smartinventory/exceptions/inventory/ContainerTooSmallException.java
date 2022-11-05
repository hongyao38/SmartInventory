package com.smartinventory.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContainerTooSmallException extends RuntimeException {
    
    public ContainerTooSmallException() {
        super("Container unable to fit this quantity!");
    }
}
