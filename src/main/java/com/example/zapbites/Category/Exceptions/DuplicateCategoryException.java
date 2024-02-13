package com.example.zapbites.Category.Exceptions;

public class DuplicateCategoryException extends RuntimeException {

    public DuplicateCategoryException(String message) {
        super(message);
    }
}
