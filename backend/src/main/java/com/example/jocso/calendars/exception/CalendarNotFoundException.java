package com.example.jocso.calendars.exception;

public class CalendarNotFoundException extends RuntimeException {
    public CalendarNotFoundException(String message) {
        super(message);
    }
}