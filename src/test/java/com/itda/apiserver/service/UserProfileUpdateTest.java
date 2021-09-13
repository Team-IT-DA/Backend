package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.dto.UpdateProfileDto;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserProfileUpdateTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("빈걸로 업데이트 하면 기존 정보 사용")
    void ifUsingPrevValThenGivenEmptyString() {
        User prevUser = userSignUp();

        String PHONE = "010-3200-1234";
        String PASSWORD = "12345";

        UpdateProfileDto updateProfileDto = new UpdateProfileDto();
        updateProfileDto.setEmail("");
        updateProfileDto.setPassword(PASSWORD);
        updateProfileDto.setName("roach");
        updateProfileDto.setTelephone(PHONE);

        userService.updateProfile(updateProfileDto, prevUser.getId());

        User user = userRepository.findById(prevUser.getId()).get();
        assertEquals(user.getEmail(), "roach@naver.com");
        assertEquals(user.getPassword(), PASSWORD);
        assertEquals(user.getPhone(), PHONE);
    }

    private User userSignUp() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setEmail("roach@naver.com");
        signUpRequestDto.setPassword("1234");
        signUpRequestDto.setName("roach");
        signUpRequestDto.setTelephone("010-1234-5678");
        return userService.signUp(signUpRequestDto);
    }

}
