package com.example.jocso.accounts.dto;

import lombok.*;

@Getter
@Setter
public class CreateAccessTokenRequestDto {
    private String refreshToken;
}
