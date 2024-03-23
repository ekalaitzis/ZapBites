package com.example.zapbites.OrderStatus.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateOrderStatusException extends RuntimeException {

    public DuplicateOrderStatusException(String message) {
        super(message);
    }
}
