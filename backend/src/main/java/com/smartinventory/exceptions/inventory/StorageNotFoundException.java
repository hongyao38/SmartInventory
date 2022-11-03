package com.smartinventory.exceptions.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StorageNotFoundException extends RuntimeException {

    public StorageNotFoundException(Long id) {
        super(String.format("Storage %ld is not found!", id));
    }

    public StorageNotFoundException(String username) {
        super(String.format("%s's storage not found!", username));
    }
}
