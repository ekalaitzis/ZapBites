package com.example.zapbites.Menu.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class MenuNotFoundException extends EntityNotFoundException {
    public MenuNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public MenuNotFoundException(String message) {
    }
}