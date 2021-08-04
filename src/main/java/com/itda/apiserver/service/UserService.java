package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpRequestDto signUpRequestDto) {
        User user = signUpRequestDto.toUser();
        userRepository.save(user);
    }
}
