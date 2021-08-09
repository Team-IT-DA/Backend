package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.LoginRequestDto;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.dto.TokenResponseDto;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.UserRepository;
import com.itda.exception.EmailDuplicationException;
import com.itda.exception.UserNotFoundException;
import com.itda.exception.WrongPasswordException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
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

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new WrongPasswordException();
        }

        String token = tokenProvider.createToken(user.getId());
        return new TokenResponseDto(token);
    }
}
