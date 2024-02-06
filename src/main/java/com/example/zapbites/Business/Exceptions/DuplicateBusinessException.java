package com.example.zapbites.Business.Exceptions;

public class DuplicateBusinessException extends RuntimeException {

    public DuplicateBusinessException(String message) {
        super(message);
    }
}
