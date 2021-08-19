package com.itda.apiserver.service;

import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.LoginRequestDto;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.dto.TokenResponseDto;
import com.itda.apiserver.exception.EmailDuplicationException;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.exception.WrongPasswordException;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public User signUp(SignUpRequestDto signUpDto) {
        verifyEmail(signUpDto.getEmail());

        User user = new User(signUpDto.getName(), signUpDto.getTelephone(),
                signUpDto.getEmail(), signUpDto.getPassword(), signUpDto.getAuthCode());
        return userRepository.save(user);
    }

    public void verifyEmail(String email) {
        int userCount = userRepository.countByEmail(email);

        if (userCount > 0) {
            throw new EmailDuplicationException();
        }
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(UserNotFoundException::new);

        if (!user.isPasswordMatching(loginRequestDto.getPassword())) {
            throw new WrongPasswordException();
        }

        String token = tokenProvider.createToken(user.getId());
        return new TokenResponseDto(token);
    }
}
