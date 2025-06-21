package com.example.calendar.accounts.controller;

import com.example.calendar.accounts.dto.*;
import com.example.calendar.accounts.service.UserService;
import jakarta.servlet.http.*;
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

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@RequestBody LoginUserRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(userService.signin(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponseDto> signup(@RequestBody SignupUserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.signup(request));
    }

    // 향후 구현: 로그아웃하면 refresh token DB에서 삭제시키는 기능
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "bye login";
    }
}
