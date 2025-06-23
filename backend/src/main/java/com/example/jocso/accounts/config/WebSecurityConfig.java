package com.example.jocso.accounts.config;

import com.example.jocso.accounts.jwt.TokenProvider; // TokenProvider 임포트 추가
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 추가
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // @EnableWebSecurity 추가
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // AbstractHttpConfigurer 추가
import org.springframework.security.config.http.SessionCreationPolicy; // SessionCreationPolicy 추가
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint; // HttpStatusEntryPoint 추가
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // UsernamePasswordAuthenticationFilter 추가
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // Spring Security 활성화
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final TokenProvider tokenProvider; // TokenProvider 주입

    // JWT 인증 필터 빈으로 등록
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    // Spring Security 기능 비활성화 (보통 정적 리소스나 H2 콘솔 등에 사용)
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
            .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**", "/error", "/favicon.ico"); // 정적 리소스 추가
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF 보호 비활성화 (JWT 사용 시 무상태이므로 비활성화)
            .csrf(AbstractHttpConfigurer::disable)

            // 2. 폼 로그인, HTTP Basic 인증 비활성화 (JWT 사용)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // 3. 세션 비활성화 (Stateless)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 4. 예외 처리 (인증 실패 시 401 Unauthorized 반환)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )

            // 5. 요청에 대한 인가 규칙 설정
            .authorizeHttpRequests(authorize -> authorize
                // 인증 없이 접근 가능한 경로
                .requestMatchers("/api/accounts/login").permitAll() // 로그인
                .requestMatchers("/api/accounts/signup").permitAll() // 회원가입
                .requestMatchers("/api/accounts/token").permitAll() // 토큰 재발급 API
                // 이 외의 모든 경로는 인증 필요
                .anyRequest().authenticated()
            )

            // 6. JWT 토큰 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
            // 요청 헤더의 JWT 토큰을 먼저 검사하여 인증을 시도합니다.
            .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 인증 관리자 관련 설정 (기존과 동일, 비밀번호 암호화 포함)
    @Bean
    public AuthenticationManager authenticationManager(
        BCryptPasswordEncoder bCryptPasswordEncoder,
        UserDetailsService userDetailsService) {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}