package com.example.calendar.accounts.dto;


import lombok.*;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String name;
    private String password;
}
