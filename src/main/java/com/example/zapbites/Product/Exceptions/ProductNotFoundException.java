package com.example.zapbites.Product.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public ProductNotFoundException(String message) {
    }
}