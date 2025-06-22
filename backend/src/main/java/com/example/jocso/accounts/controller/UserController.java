package com.example.jocso.accounts.controller;

import com.example.jocso.accounts.dto.*;
import com.example.jocso.accounts.service.TokenService;
import com.example.jocso.accounts.service.UserService;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@Valid @RequestBody LoginUserRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.signin(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponseDto> signup(@Valid @RequestBody SignupUserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.signup(request));
    }

    // 향후 구현: 로그아웃하면 refresh token DB에서 삭제시키는 기능
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "bye login";
    }

    // refresh token을 바탕으로 새로운 access token 발급
    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken(@RequestBody CreateAccessTokenRequestDto request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CreateAccessTokenResponseDto(newAccessToken));
    }
}
