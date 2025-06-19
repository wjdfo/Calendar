package com.example.calendar.config.jwt;

import com.example.calendar.accounts.domain.User;
import com.example.calendar.accounts.jwt.JwtProperties;
import com.example.calendar.accounts.jwt.TokenProvider;
import com.example.calendar.accounts.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    // JWT 서명 키를 Key 객체로 미리 생성
    private Key secretKey;

    @BeforeEach // 각 테스트 시작 전 실행
    void setUp() {
        secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        // 테스트마다 유저 데이터를 새로 생성하지 않고, 필요 시에만 생성하도록 변경 (optional)
        // userRepository.deleteAll(); // 필요하다면 각 테스트 전 DB 초기화
    }

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // Given
        User testUser = userRepository.save(User.builder()
            .email("user@gmail.com")
            .name("test")
            .password("test")
            .build());

        // When
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // Then
        Long userId = Jwts.parser()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
        userRepository.delete(testUser); // 테스트 후 데이터 정리
    }

    @DisplayName("validToken(): 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {
        // Given
        String token = JwtFactory.builder()
            .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis())) // 과거 날짜로 설정하여 만료시킴
            .build()
            .createToken(jwtProperties);

        // When
        boolean result = tokenProvider.validToken(token);

        // Then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        // Given
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        // When
        boolean result = tokenProvider.validToken(token);

        // Then
        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication(): 토큰 기반 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // Given
        String userEmail = "user@gmail.com";
        String token = JwtFactory.builder()
            .subject(userEmail)
            .build()
            .createToken(jwtProperties);

        // When
        Authentication authentication = tokenProvider.getAuthentication(token);

        // Then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("getUserId(): 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // Given
        Long userId = 1L;

        String token = JwtFactory.builder()
            .claims(Map.of("id", userId))
            .build()
            .createToken(jwtProperties);

        // When
        Long userIdByToken = tokenProvider.getUserId(token);

        // Then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}
