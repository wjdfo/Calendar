package com.example.calendar.accounts.config;


import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    // Spring Security 기능 비활성화
    // 기본적으로 Spring Security는 모든 접근을 차단함 (관리자 제외)
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
            .requestMatchers(toH2Console())
            .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 보호 비활성화 (API 서버의 경우 토큰 기반 인증을 사용한다면 비활성화하기도 합니다.)
            .csrf(csrf -> csrf.disable())

            // 요청에 대한 인가 규칙 설정
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/login", "/api/signup").permitAll() // 해당 경로는 인가 없이 접근 가능
                .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
            )
            // 폼 로그인 설정
            .formLogin(formLogin -> formLogin
                .loginPage("/api/login") // 로그인 페이지 URL
                .defaultSuccessUrl("/api/calendars", true) // 로그인 성공 시 리디렉션될 기본 URL (항상 리디렉션)
                // .successHandler(myCustomSuccessHandler) 이거 추가해주자.
                .usernameParameter("email") // 사용자 ID로 사용할 파라미터명 (기본은 username)
                .passwordParameter("password") // 비밀번호로 사용할 파라미터명 (기본은 password)
                .failureUrl("/api/login?error=true") // 로그인 실패 시 이동할 URL
            )
            // 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/api/logout") // 로그아웃 처리 URL (기본은 /logout)
                .logoutSuccessUrl("/api/login?logout") // 로그아웃 성공 시 리디렉션될 URL
                .invalidateHttpSession(true) // 로그아웃 이후 세션 전체 삭제
                .deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
            );

        return http.build(); // HttpSecurity 설정을 빌드하여 SecurityFilterChain 반환
    }

    // 인증 관리자 관련 설정
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
