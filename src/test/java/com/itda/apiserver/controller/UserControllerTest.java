package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.EmailVerificationRequestDto;
import com.itda.apiserver.dto.LoginRequestDto;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.dto.TokenResponseDto;
import com.itda.apiserver.jwt.TokenExtractor;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(value = {TokenProvider.class, TokenExtractor.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private User user;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원 가입 기능 테스트")
    void signUp() throws Exception {

        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setName("김나연");
        signUpRequestDto.setEmail("yeon@gmail.com");
        signUpRequestDto.setTelephone("01022223333");
        signUpRequestDto.setPassword("1234");

        when(userService.signUp(signUpRequestDto)).thenReturn(user);

        mockMvc.perform(post("/api/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("이메일 중복 확인 기능 테스트")
    void verifyEmail() throws Exception {

        EmailVerificationRequestDto emailDto = new EmailVerificationRequestDto();
        emailDto.setEmail("yeon@gmail.com");

        mockMvc.perform(get("/api/duplicateEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 기능 테스트")
    void login() throws Exception {

        LoginRequestDto loginDto = new LoginRequestDto();
        loginDto.setEmail("yeon@gmail.com");
        loginDto.setPassword("yeon1234");

        TokenResponseDto tokenDto = new TokenResponseDto("thisIsToken");
        when(userService.login(any(LoginRequestDto.class))).thenReturn(tokenDto);

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andDo(print());
    }

}
