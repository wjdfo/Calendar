package com.example.jocso.calendars.exception;

public class UnauthorizedCalendarAccessException extends RuntimeException {
    public UnauthorizedCalendarAccessException(String message) {
        super(message);
    }
}