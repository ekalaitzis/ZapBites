package com.example.zapbites.OrderProduct.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateOrderProductException extends RuntimeException {

    public DuplicateOrderProductException(String message) {
        super(message);
    }
}
