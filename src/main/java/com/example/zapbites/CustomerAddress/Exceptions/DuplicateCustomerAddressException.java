package com.example.zapbites.CustomerAddress.Exceptions;

public class DuplicateCustomerAddressException extends RuntimeException {

    public DuplicateCustomerAddressException(String message) {
        super(message);
    }
}
