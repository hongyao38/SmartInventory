package com.smartinventory.exceptions.exceptionhandler;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.smartinventory.exceptions.inventory.consumption.InvalidConsumptionException;
import com.smartinventory.exceptions.inventory.container.ContainerNotFoundException;
import com.smartinventory.exceptions.token.EmailAlreadyVerifiedException;
import com.smartinventory.exceptions.token.InvalidTokenException;
import com.smartinventory.exceptions.user.PasswordNotMatchingException;
import com.smartinventory.exceptions.user.UserEmailNotFoundException;
import com.smartinventory.exceptions.user.UserEmailTakenException;
import com.smartinventory.exceptions.user.UserIdNotFoundException;
import com.smartinventory.exceptions.user.UsernameTakenException;

@ControllerAdvice
public class RestExceptionHandler {

        // handles HTTPStatus 404: Not found
        @ExceptionHandler({ ContainerNotFoundException.class,
                        UserEmailNotFoundException.class, UserIdNotFoundException.class, InvalidTokenException.class,
                        InvalidTokenException.class })
        public ResponseEntity<Object> handle404Exception(RuntimeException e) {
                HttpStatus status = HttpStatus.NOT_FOUND;
                RestException restException = new RestException(e.getMessage(), status,
                                ZonedDateTime.now());
                System.out.println("Ran 404handler");
                return new ResponseEntity<>(restException, status);
        }

        // handles HTTPStatus 400: Bad Request
        @ExceptionHandler({ InvalidConsumptionException.class,
                        EmailAlreadyVerifiedException.class, PasswordNotMatchingException.class,
                        UserEmailTakenException.class,
                        UsernameTakenException.class })
        public ResponseEntity<Object> handle400Exception(RuntimeException e) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                RestException restException = new RestException(e.getMessage(), status,
                                ZonedDateTime.now());
                System.out.println("Ran 400handler");

                return new ResponseEntity<>(restException, status);
        }

}
