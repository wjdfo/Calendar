package com.example.calendar.accounts.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SignupUserRequestDto {
    private String email;
    private String name;
    private String password;
}
