package com.example.calendar.accounts.controller;

import com.example.calendar.accounts.dto.AddUserRequest;
import com.example.calendar.accounts.dto.LoginUserRequestDto;
import com.example.calendar.accounts.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/api/login")
    public String login(LoginUserRequestDto request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());

        return "login";
    }

    @PostMapping("/api/signup")
    public String signup(AddUserRequest request) {
        userService.save(request);

        return "hello login";
    }

    @GetMapping("/api/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "bye login";
    }
}
