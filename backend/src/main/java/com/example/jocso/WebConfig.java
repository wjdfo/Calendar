package com.example.jocso;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Spring Security에서 CORS를 처리하므로 여기서는 설정하지 않습니다.
}
