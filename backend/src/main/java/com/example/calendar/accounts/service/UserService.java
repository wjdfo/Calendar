package com.example.calendar.accounts.service;

import com.example.calendar.accounts.domain.*;
import com.example.calendar.accounts.dto.*;
import com.example.calendar.accounts.jwt.TokenProvider;
import com.example.calendar.accounts.repository.*;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginUserResponseDto signin(LoginUserRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials: User not found."));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials: Incorrect password.");
        }

        String refreshToken = tokenProvider.generateToken(user, Duration.ofDays(7));

        // RefreshToken 객체를 새로 생성하여 저장 or 기존 RefreshToken이 있다면 업데이트합니다.
        refreshTokenRepository.findByUserId(user.getId()).ifPresentOrElse(
            // Update: 이미 리프레시 토큰이 존재하는 경우
            existingToken -> {
                existingToken.update(refreshToken);
                refreshTokenRepository.save(existingToken);
            },
            // 없다면 새로 저장
            () -> refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken))
        );

        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(1));

        return new LoginUserResponseDto(accessToken, refreshToken);
    }

    public SignupUserResponseDto signup(SignupUserRequestDto request) {
        // 이메일 중복을 먼저 걸러줌 -> 사용자 친화적인 에러 메시지 + 객체 생성 낭비 방지
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        User newUser = User.builder()
            .email(request.getEmail())
            .name(request.getName())
            .password(encodedPassword)
            .build();

        try {
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) { // 데이터 무결성 조건 위반한 경우
            throw new IllegalArgumentException("Failed to register user due to data integrity violation.", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user.", e);
        }

        return new SignupUserResponseDto(newUser.getId());
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
