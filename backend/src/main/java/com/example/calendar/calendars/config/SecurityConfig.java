package com.example.calendar.calendars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity // 이거 꼭 추가해야 Spring Security 설정 적용됨!
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()   // GET 허용
                        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()  // POST 허용
                        .requestMatchers(HttpMethod.PUT, "/api/**").permitAll()   // PUT 허용
                        .requestMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                        .anyRequest().permitAll()
                )
                .build();
    }
}
