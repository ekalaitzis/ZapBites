package com.example.zapbites.CustomerAddress.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateCustomerAddressException extends RuntimeException {

    public DuplicateCustomerAddressException(String message) {
        super(message);
    }
}
