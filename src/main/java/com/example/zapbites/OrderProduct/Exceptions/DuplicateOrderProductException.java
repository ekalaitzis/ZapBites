package com.example.zapbites.OrderProduct.Exceptions;

public class DuplicateOrderProductException extends RuntimeException {

    public DuplicateOrderProductException(String message) {
        super(message);
    }
}
