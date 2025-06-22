package com.example.calendar.accounts.dto;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ErrorResponseDto {
    private String message;
    private String errorCode;
    private List<String> details;

    public ErrorResponseDto(String message, String errorCode) {
        this(message, errorCode, null);
    }

    public ErrorResponseDto(String message) {
        this(message, null, null);
    }
}
