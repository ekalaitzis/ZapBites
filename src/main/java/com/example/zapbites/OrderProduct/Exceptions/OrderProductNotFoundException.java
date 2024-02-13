package com.example.zapbites.OrderProduct.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class OrderProductNotFoundException extends EntityNotFoundException {
    public OrderProductNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public OrderProductNotFoundException(String message) {
    }
}