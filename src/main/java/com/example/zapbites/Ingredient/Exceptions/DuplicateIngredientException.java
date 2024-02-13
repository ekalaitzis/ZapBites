package com.example.zapbites.Ingredient.Exceptions;

public class DuplicateIngredientException extends RuntimeException {

    public DuplicateIngredientException(String message) {
        super(message);
    }
}
