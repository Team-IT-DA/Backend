package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.EmailVerificationRequestDto;
import com.itda.apiserver.dto.LoginRequestDto;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.UserRepository;
import com.itda.exception.EmailDuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SignUpRequestDto signUpRequestDto;

    @MockBean
    private User user;

    @MockBean
    private EmailVerificationRequestDto emailRequestDto;

    @MockBean
    private LoginRequestDto loginRequestDto;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("회원 가입 기능 테스트")
    void signUp() {
        userService.signUp(signUpRequestDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("이메일 중복 확인 기능 테스트, 중복된 이메일이 없는 경우")
    void verifyEmail() {
        when(emailRequestDto.getEmail()).thenReturn("yeon@gmail.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.verifyEmail(emailRequestDto.getEmail());

        verify(emailRequestDto, times(1)).getEmail();
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("이메일 중복 확인 기능 테스트, 이미 이메일이 존재하는 경우")
    void verifyEmailFail() {
        when(emailRequestDto.getEmail()).thenReturn("yeon@gmail.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(EmailDuplicationException.class, () -> userService.verifyEmail(emailRequestDto.getEmail()));

        verify(emailRequestDto, times(1)).getEmail();
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("로그인 기능 테스트")
    void login() {
        when(loginRequestDto.getEmail()).thenReturn("yeon@gmail.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(loginRequestDto.getPassword()).thenReturn("yeon1234");
        when(user.getPassword()).thenReturn("yeon1234");
        when(tokenProvider.createToken(anyLong())).thenReturn("token");

        userService.login(loginRequestDto);

        verify(loginRequestDto, times(1)).getEmail();
        verify(loginRequestDto, times(1)).getPassword();
        verify(user, times(1)).getPassword();
        verify(userRepository, times(1)).findByEmail(anyString());
    }

}
