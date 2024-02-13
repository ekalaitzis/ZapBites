package com.example.zapbites.Category.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public CategoryNotFoundException(String message) {
    }
}