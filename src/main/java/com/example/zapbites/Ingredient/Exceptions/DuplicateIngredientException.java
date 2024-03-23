package com.example.zapbites.Ingredient.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateIngredientException extends RuntimeException {

    public DuplicateIngredientException(String message) {
        super(message);
    }
}
