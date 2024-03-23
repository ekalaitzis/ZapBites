package com.example.zapbites.Ingredient.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class IngredientNotFoundException extends EntityNotFoundException {
    public IngredientNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }
}