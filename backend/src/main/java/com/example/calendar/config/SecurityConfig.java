package com.example.calendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 이거 꼭 추가해야 Spring Security 설정 적용됨!
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // 최신 방식으로 변경
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
