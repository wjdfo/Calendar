package com.example.calendar.accounts.service;

import com.example.calendar.accounts.domain.User;
import com.example.calendar.accounts.dto.AddUserRequest;
import com.example.calendar.accounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
            .email(dto.getEmail())
            .name(dto.getName())
            .password(bCryptPasswordEncoder.encode(dto.getPassword()))
            .build()).getId();
    }
}
