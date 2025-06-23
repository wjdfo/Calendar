package com.example.jocso.exception;

import com.example.jocso.accounts.dto.ErrorResponseDto;
import java.util.List;
import java.util.stream.Collectors;

import com.example.jocso.calendars.exception.CalendarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 유효성 검사 실패 시
    @ExceptionHandler(MethodArgumentNotValidException.class) // 인자에 있는 예외를 보고 핸들러 호출함
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponseDto("Validation Failed", "INVALID_INPUT", errors));
    }

    // 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorCode = "BAD_REQUEST";
        if (ex.getMessage().contains("already exists")) {
            errorCode = "DUPLICATE_RESOURCE";
        } else if (ex.getMessage().contains("not found")) {
            errorCode = "RESOURCE_NOT_FOUND";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponseDto(ex.getMessage(), errorCode));
    }

    // 401 Unauthorized
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponseDto("Authentication failed: " + ex.getMessage(), "AUTH_FAILED"));
    }

    // 403 Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponseDto("Access denied: You do not have sufficient permissions.", "ACCESS_DENIED"));
    }

    // 404 Not Found
    @ExceptionHandler(CalendarNotFoundException.class)
    public ResponseEntity<String> handleNotFound(CalendarNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    // 500 Internal Server Error
    @ExceptionHandler(Exception.class) // 최상위 예외
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex) {
        // 로그에는 상세 스택 트레이스를 기록하고, 클라이언트에게는 일반적인 오류 메시지를 보냅니다.
        System.err.println("Unexpected server error: " + ex.getMessage());
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponseDto("An unexpected server error occurred. Please try again later.", "INTERNAL_ERROR"));
    }
}
