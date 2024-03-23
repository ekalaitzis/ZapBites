package com.example.zapbites.CustomerAddress.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CustomerAddressNotFoundException extends EntityNotFoundException {
    public CustomerAddressNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public CustomerAddressNotFoundException(String message) {
        super(message);
    }
}