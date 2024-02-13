package com.example.zapbites.Customer.Exceptions;

public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException(String message) {
        super(message);
    }
}
