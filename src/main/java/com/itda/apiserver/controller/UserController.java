package com.itda.apiserver.controller;

import com.itda.apiserver.dto.*;
import com.itda.apiserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    public ApiResult<Void> signUp(@RequestBody SignUpRequestDto requestDto) {
        userService.signUp(requestDto);
        return ApiResult.ok(null);
    }

    @GetMapping("/api/duplicateEmail")
    public ApiResult<Void> verifyEmail(@RequestBody EmailVerificationRequestDto requestDto) {
        userService.verifyEmail(requestDto.getEmail());
        return ApiResult.ok(null);
    }

    @PostMapping("/api/login")
    public ApiResult<TokenResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        TokenResponseDto response = userService.login(requestDto);
        return ApiResult.ok(response);
    }
}
