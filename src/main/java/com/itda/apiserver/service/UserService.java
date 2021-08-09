package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.repository.UserRepository;
import com.itda.exception.EmailDuplicationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(SignUpRequestDto signUpDto) {
        verifyEmail(signUpDto.getEmail());

        User user = new User(signUpDto.getName(), signUpDto.getTelephone(),
                signUpDto.getEmail(), signUpDto.getPassword(), signUpDto.getAuthCode());
        return userRepository.save(user);
    }

    public void verifyEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            throw new EmailDuplicationException();
        }
    }
}
