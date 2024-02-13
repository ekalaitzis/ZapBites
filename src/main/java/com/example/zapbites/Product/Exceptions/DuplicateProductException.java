package com.example.zapbites.Product.Exceptions;

public class DuplicateProductException extends RuntimeException {

    public DuplicateProductException(String message) {
        super(message);
    }
}
