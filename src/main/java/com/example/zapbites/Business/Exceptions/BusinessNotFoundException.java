package com.example.zapbites.Business.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class BusinessNotFoundException extends EntityNotFoundException {
    public BusinessNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public BusinessNotFoundException(String message) {
        super(message);
    }
}