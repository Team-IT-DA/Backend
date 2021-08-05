package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @MockBean
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

}
