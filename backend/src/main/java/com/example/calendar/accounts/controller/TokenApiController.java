package com.example.calendar.accounts.controller;

import com.example.calendar.accounts.dto.CreateAccessTokenRequestDto;
import com.example.calendar.accounts.dto.CreateAccessTokenResponseDto;
import com.example.calendar.accounts.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken(@RequestBody
        CreateAccessTokenRequestDto request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CreateAccessTokenResponseDto(newAccessToken));
    }
}
