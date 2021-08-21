package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.CartRequestDto;
import com.itda.apiserver.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @LoginRequired
    public ApiResult<Void> addProductToCart(@RequestBody CartRequestDto cartRequestDto, @UserId Long userId) {
        cartService.addProduct(cartRequestDto, userId);
        return ApiResult.ok(null);
    }

}
