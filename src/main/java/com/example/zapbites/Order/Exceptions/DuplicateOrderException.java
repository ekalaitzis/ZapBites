package com.example.zapbites.Order.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateOrderException extends RuntimeException {

    public DuplicateOrderException(String message) {
        super(message);
    }
}
