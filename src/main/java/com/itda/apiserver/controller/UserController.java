package com.itda.apiserver.controller;

import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.SignUpRequestDto;
import com.itda.apiserver.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/join")
    public ApiResult<Void> signUp(SignUpRequestDto requestDto) {
        userService.signUp(requestDto);
        return ApiResult.ok(null);
    }
}
