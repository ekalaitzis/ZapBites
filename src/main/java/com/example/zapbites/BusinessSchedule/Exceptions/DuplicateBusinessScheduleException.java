package com.example.zapbites.BusinessSchedule.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateBusinessScheduleException extends RuntimeException {

    public DuplicateBusinessScheduleException(String message) {
        super(message);
    }
}
