package com.itda.apiserver.oauth;

import com.itda.apiserver.dto.TokenResponseDto;
import com.itda.apiserver.jwt.TokenExtractor;
import com.itda.apiserver.jwt.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OauthController.class)
@Import(value = {TokenProvider.class, TokenExtractor.class})
public class OauthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocialLoginService socialLoginService;

    @Test
    @DisplayName("네이버로 소셜로그인 하는 기능 테스트")
    void neverLogin() throws Exception {

        when(socialLoginService.naverLogin("authorizationCode")).thenReturn(new TokenResponseDto("thisIsToken", "yeon"));

        mockMvc.perform(get("/api/login/naver")
                .param("code", "authorizationCode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.name").isString())
                .andDo(print());
    }

    @Test
    @DisplayName("카카오로 소셜로그인 하는 기능 테스트")
    void kakaoLogin() throws Exception {

        when(socialLoginService.kakaoLogin("authorizationCode")).thenReturn(new TokenResponseDto("thisIsToken", "yeon"));

        mockMvc.perform(get("/api/login/kakao")
                .param("code", "authorizationCode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").exists())
                .andExpect(jsonPath("$.data.name").isString())
                .andDo(print());
    }
}
