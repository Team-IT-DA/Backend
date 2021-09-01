package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.OrderRequestDto;
import com.itda.apiserver.dto.OrderResponseDto;
import com.itda.apiserver.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @LoginRequired
    @PostMapping("/api/order")
    public ApiResult<OrderResponseDto> order(@UserId Long userId, @RequestBody OrderRequestDto request) {
        return ApiResult.ok(orderService.order(userId, request));
    }
}
