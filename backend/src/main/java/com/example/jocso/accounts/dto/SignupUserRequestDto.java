package com.example.jocso.accounts.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class SignupUserRequestDto {
    @NotBlank(message = "E-mail cannot be empty.")
    @Email(message = "Invalid E-mail format.")
    private String email;

    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @NotBlank(message = "Password cannot be empty.")
    private String password;
}
