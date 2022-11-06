package com.smartinventory.exceptions.inventory.block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BlockExistsException extends RuntimeException {

    public BlockExistsException() {
        super(String.format("Block exists!"));
    }

}
