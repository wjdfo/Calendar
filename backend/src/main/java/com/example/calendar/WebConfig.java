package com.example.calendar;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 캘린더 API
        registry.addMapping("/api/calendars/**") // 모든 경로에 대해 CORS 허용
            .allowedOrigins("http://localhost:3000") // 출처 허용
            .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 Http method
            .allowedHeaders( // 허용할 Http headers
                "Authorization",
                "Content-Type"
            )
            .allowCredentials(true); // 자격 증명 허용

        // 로그인 API
        registry.addMapping("/api/accounts/login")
            .allowedOrigins("http://localhost:3000")
            .allowedHeaders("Content-Type")
            .allowedMethods("POST", "OPTIONS");

        // 회원가입 API
        registry.addMapping("/api/accounts/signup")
            .allowedOrigins("http://localhost:3000")
            .allowedHeaders("Content-Type")
            .allowedMethods("POST");
    }
}