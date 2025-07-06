package com.example.jocso.accounts.controller;

import com.example.jocso.accounts.dto.LoginUserRequestDto;
import com.example.jocso.accounts.dto.SignupUserRequestDto;
import com.example.jocso.accounts.dto.SignupUserResponseDto;
import com.example.jocso.accounts.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public UserService userService() {
            return mock(UserService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private LoginUserRequestDto loginUserRequestDto;

    @BeforeEach
    void setUp() {
        loginUserRequestDto = new LoginUserRequestDto("test@example.com", "password");
    }

    @DisplayName("회원가입에 성공한다.")
    @Test
    void signupTest() throws Exception {
        // given
        final SignupUserRequestDto userRequest = new SignupUserRequestDto("test@example.com", "testuser", "password");
        final SignupUserResponseDto userResponse = new SignupUserResponseDto(1L);

        when(userService.signup(any(SignupUserRequestDto.class))).thenReturn(userResponse);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user_id").value(1L));
    }

    @DisplayName("로그인 성공 시, 액세스 토큰은 Body에, 리프레시 토큰은 HttpOnly 쿠키로 반환한다.")
    @Test
    void loginTest() throws Exception {
        // given
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", "test-access-token");
        tokens.put("refreshToken", "test-refresh-token");

        when(userService.signin(any(LoginUserRequestDto.class))).thenReturn(tokens);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/accounts/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserRequestDto)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("test-access-token"))
                .andExpect(cookie().exists("refresh_token"))
                .andExpect(cookie().httpOnly("refresh_token", true))
                .andExpect(cookie().secure("refresh_token", false))
                .andExpect(cookie().value("refresh_token", "test-refresh-token"));
    }
}
