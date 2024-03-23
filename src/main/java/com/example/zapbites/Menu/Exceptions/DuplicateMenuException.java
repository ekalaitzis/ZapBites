package com.example.zapbites.Menu.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateMenuException extends RuntimeException {

    public DuplicateMenuException(String message) {
        super(message);
    }
}
