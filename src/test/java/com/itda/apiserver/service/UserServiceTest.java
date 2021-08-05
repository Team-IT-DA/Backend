package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Test
    @DisplayName("회원 가입 기능 테스트")
    void signUp() {
        when(signUpRequestDto.toUser()).thenReturn(user);

        userService.signUp(signUpRequestDto);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
