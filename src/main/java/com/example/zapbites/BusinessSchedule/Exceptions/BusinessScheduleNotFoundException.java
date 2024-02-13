package com.example.zapbites.BusinessSchedule.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class BusinessScheduleNotFoundException extends EntityNotFoundException {
    public BusinessScheduleNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }

    public BusinessScheduleNotFoundException(String message) {
    }
}