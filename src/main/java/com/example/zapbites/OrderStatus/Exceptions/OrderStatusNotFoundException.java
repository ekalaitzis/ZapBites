package com.example.zapbites.OrderStatus.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class OrderStatusNotFoundException extends EntityNotFoundException {
    public OrderStatusNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public OrderStatusNotFoundException(String message) {
        super(message);
    }
}