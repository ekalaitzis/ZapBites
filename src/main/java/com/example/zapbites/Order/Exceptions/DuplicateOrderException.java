package com.example.zapbites.Order.Exceptions;

public class DuplicateOrderException extends RuntimeException {

    public DuplicateOrderException(String message) {
        super(message);
    }
}
