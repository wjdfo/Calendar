package com.example.calendar.accounts.dto;

import lombok.*;

@Getter
@Setter
public class CreateAccessTokenRequestDto {
    private String refreshToken;
}
