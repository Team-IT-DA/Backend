package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.domain.User;
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
    public ApiResult<Void> verifyEmail(@RequestParam String email) {
        userService.verifyEmail(email);
        return ApiResult.ok(null);
    }

    @LoginRequired
    @PutMapping("/api/myPage/user")
    public ApiResult<Void> updateProfile(@RequestBody UpdateProfileDto updateProfileDto, @UserId Long id) {
        userService.updateProfile(updateProfileDto, id);
        return ApiResult.ok(null);
    }


    @LoginRequired
    @GetMapping("/api/myPage/user")
    public ApiResult<SellerProfileDto> getProfile(@UserId Long userId) {
        User profile = userService.getProfile(userId);
        return ApiResult.ok(SellerProfileDto.from(profile));
    }


    @PostMapping("/api/login")
    public ApiResult<TokenResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        TokenResponseDto response = userService.login(requestDto);
        return ApiResult.ok(response);
    }

    @LoginRequired
    @PostMapping("/api/seller")
    public ApiResult<Void> enrollSeller(@RequestBody AddSellerInfoDto addSellerInfoDto, @UserId Long userId) {
        userService.enrollSeller(addSellerInfoDto, userId);
        return ApiResult.ok(null);
    }

    @LoginRequired
    @GetMapping("/api/myPage/seller")
    public ApiResult<ShowSellerProfileDto> showSellerProfile(@UserId Long userId) {
        return ApiResult.ok(userService.getSellerProfile(userId));
    }
}
