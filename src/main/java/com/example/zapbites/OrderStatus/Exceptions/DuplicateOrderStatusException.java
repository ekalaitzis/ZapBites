package com.example.zapbites.OrderStatus.Exceptions;

public class DuplicateOrderStatusException extends RuntimeException {

    public DuplicateOrderStatusException(String message) {
        super(message);
    }
}
