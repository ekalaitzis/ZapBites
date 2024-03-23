package com.example.zapbites.Category.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateCategoryException extends RuntimeException {

    public DuplicateCategoryException(String message) {
        super(message);
    }
}
