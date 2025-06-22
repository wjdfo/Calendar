package com.example.jocso.accounts.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class LoginUserRequestDto {
    @NotBlank(message = "E-mail cannot be empty.")
    @Email(message = "Invalid E-mail foramt.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    private String password;
}
