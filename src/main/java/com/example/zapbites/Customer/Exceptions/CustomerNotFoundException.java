package com.example.zapbites.Customer.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public CustomerNotFoundException(String message) {
    }
}