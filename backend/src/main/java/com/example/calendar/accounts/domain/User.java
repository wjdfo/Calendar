package com.example.calendar.accounts.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 자동 증가 기능 사용
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", updatable = false, nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
