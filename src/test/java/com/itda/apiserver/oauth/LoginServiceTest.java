package com.itda.apiserver.oauth;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.oauth.naver.NaverLoginService;
import com.itda.apiserver.oauth.naver.NaverUserInfo;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @MockBean
    private NaverLoginService naverLoginService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenProvider tokenProvider;

    @Mock
    private NaverUserInfo naverUserInfo;

    @Mock
    private User user;

    @Mock
    private SocialProfile socialProfile;

    @Test
    @DisplayName("네이버로 소셜 로그인 기능 테스트")
    void socialLogin() {

        when(naverLoginService.getUserInfo(anyString())).thenReturn(naverUserInfo);
        when(naverUserInfo.getEmail()).thenReturn("yeon@test.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        loginService.socialLogin(anyString());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(tokenProvider, times(1)).createToken(anyLong());

    }
}
