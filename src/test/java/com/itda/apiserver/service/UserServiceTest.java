package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.EmailVerificationRequestDto;
import com.itda.apiserver.dto.LoginRequestDto;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.dto.UpdateProfileDto;
import com.itda.apiserver.exception.EmailDuplicationException;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.exception.WrongPasswordException;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

    @Mock
    private SignUpRequestDto signUpRequestDto;

    @Mock
    private User user;

    @Mock
    private EmailVerificationRequestDto emailRequestDto;

    @Mock
    private LoginRequestDto loginRequestDto;

    @Mock
    private UpdateProfileDto updateProfileDto;

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
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        userService.verifyEmail(emailRequestDto.getEmail());

        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    @DisplayName("이메일 중복 확인 기능 테스트, 이미 이메일이 존재하는 경우")
    void verifyEmailFail() {
        when(emailRequestDto.getEmail()).thenReturn("yeon@gmail.com");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailDuplicationException.class, () -> userService.verifyEmail(emailRequestDto.getEmail()));

        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    @DisplayName("로그인 기능 테스트")
    void login() {
        when(loginRequestDto.getEmail()).thenReturn("yeon@gmail.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(loginRequestDto.getPassword()).thenReturn("yeon1234");
        when(user.isPasswordMatching(anyString())).thenReturn(true);
        when(tokenProvider.createToken(anyLong())).thenReturn("token");

        userService.login(loginRequestDto);

        verify(loginRequestDto, times(1)).getEmail();
        verify(loginRequestDto, times(1)).getPassword();
        verify(user, times(1)).isPasswordMatching(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(tokenProvider, times(1)).createToken(anyLong());
    }

    @Test
    @DisplayName("로그인 기능 테스트, 이메일이 일치하지 않는 경우")
    void loginFail() {
        when(loginRequestDto.getEmail()).thenReturn("stranger@gmail.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.login(loginRequestDto));

        verify(loginRequestDto, times(1)).getEmail();
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(loginRequestDto, times(0)).getPassword();
        verify(user, times(0)).isPasswordMatching(anyString());
        verify(tokenProvider, times(0)).createToken(anyLong());
    }

    @Test
    @DisplayName("로그인 기능 테스트, 비밀번호가 일치하지 않는 경우")
    void wrongPassword() {
        when(loginRequestDto.getEmail()).thenReturn("yeon@gmail.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(loginRequestDto.getPassword()).thenReturn("weirdPassword");
        when(user.isPasswordMatching(anyString())).thenReturn(false);

        assertThrows(WrongPasswordException.class, () -> userService.login(loginRequestDto));

        verify(loginRequestDto, times(1)).getEmail();
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(loginRequestDto, times(1)).getPassword();
        verify(user, times(1)).isPasswordMatching(anyString());
        verify(tokenProvider, times(0)).createToken(anyLong());
    }

    @Test
    @DisplayName("회원 정보 업데이트 서비스")
    void correctUpdateProfile() {
        String PHONE = "010-3200-1234";
        String PASSWORD = "12345";
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        updateProfileDto.setEmail("dodo@naver.com");
        updateProfileDto.setPassword(PASSWORD);
        updateProfileDto.setName("roach");
        updateProfileDto.setTelephone(PHONE);

        userService.updateProfile(updateProfileDto, 1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any());
    }

}
