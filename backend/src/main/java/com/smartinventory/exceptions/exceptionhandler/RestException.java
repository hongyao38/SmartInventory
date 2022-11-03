package com.smartinventory.exceptions.exceptionhandler;


import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class RestException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    public RestException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    
}
