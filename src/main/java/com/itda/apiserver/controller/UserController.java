package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.*;
import com.itda.apiserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @LoginRequired
    @PutMapping("/api/myPage/user")
    public ApiResult<Void> updateProfile(@RequestBody UpdateProfileDto updateProfileDto, @UserId Long id) {
        userService.updateProfile(updateProfileDto, id);
        return ApiResult.ok(null);
    }

    @PostMapping("/api/login")
    public ApiResult<TokenResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        TokenResponseDto response = userService.login(requestDto);
        return ApiResult.ok(response);
    }
}
