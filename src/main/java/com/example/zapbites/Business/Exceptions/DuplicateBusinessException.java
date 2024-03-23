package com.example.zapbites.Business.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateBusinessException extends RuntimeException {

    public DuplicateBusinessException(String message) {
        super(message);
    }
}
