package com.example.calendar.accounts.dto;

import lombok.*;

@RequiredArgsConstructor
@Getter
public class LoginUserResponseDto {
    private final String accessToken;
    private final String refreshToken;
}
