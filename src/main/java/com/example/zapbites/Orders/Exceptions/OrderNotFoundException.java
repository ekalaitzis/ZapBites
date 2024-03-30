package com.example.zapbites.Orders.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}