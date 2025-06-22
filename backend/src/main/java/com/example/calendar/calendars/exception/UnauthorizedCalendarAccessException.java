package com.example.calendar.calendars.exception;

public class UnauthorizedCalendarAccessException extends RuntimeException {
    public UnauthorizedCalendarAccessException(String message) {
        super(message);
    }
}