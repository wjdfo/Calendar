package com.example.jocso.calendars.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CalendarNotFoundException.class)
    public ResponseEntity<String> handleNotFound(CalendarNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedCalendarAccessException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedCalendarAccessException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }

}
