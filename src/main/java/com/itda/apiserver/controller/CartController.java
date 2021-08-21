package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.CartRequestDto;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @LoginRequired
    public ApiResult<ShopBasket> getAllProductsFromCart(@UserId Long userId) {
        return ApiResult.ok(cartService.getProducts(userId));
    }

}
