package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.*;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.itda.apiserver.TestHelper.createSignUpRequestDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원 가입 기능 테스트")
    void signUp() throws Exception {

        SignUpRequestDto signUpRequestDto = createSignUpRequestDto();

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
        emailDto.setEmail("roach@gmail.com");

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
        loginDto.setEmail("new@gmail.com");
        loginDto.setPassword("1234");

        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setEmail("new@gmail.com");
        signUpRequestDto.setName("yeon");
        signUpRequestDto.setTelephone("01011112222");
        signUpRequestDto.setPassword("1234");

        userService.signUp(signUpRequestDto);

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.token").isString())
                .andDo(print());
    }

    @Test
    @DisplayName("유저 프로필 업데이트 테스트")
    void updateProfile() throws Exception {

        UpdateProfileDto updateProfileDto = new UpdateProfileDto();
        updateProfileDto.setEmail("yeon@gmail.com");
        updateProfileDto.setName("yeon");
        updateProfileDto.setTelephone("010-1234-5678");
        updateProfileDto.setPassword("yeon1234");

        User user = singUp();
        String token = "Bearer " + tokenProvider.createToken(user.getId());

        mockMvc.perform(put("/api/myPage/user")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProfileDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 판매자 프로필 등록 테스트")
    void enrollSellerTest() throws Exception {

        AddSellerInfoDto addSellerInfoDto = new AddSellerInfoDto();
        addSellerInfoDto.setImgUrl("https://www.naver.com");
        addSellerInfoDto.setDescription("판매자!");

        User user = singUp();
        String token = "Bearer " + tokenProvider.createToken(user.getId());

        mockMvc.perform(post("/api/seller")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSellerInfoDto)))
                        .andExpect(status().isOk());

    }

    private User singUp() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setName("김나연");
        signUpRequestDto.setEmail("yeon12@gmail.com");
        signUpRequestDto.setTelephone("01022223333");
        signUpRequestDto.setPassword("1234");

        return userService.signUp(signUpRequestDto);
    }

}
