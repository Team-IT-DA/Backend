package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.CartProduct;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @LoginRequired
    public ApiResult<Void> updateProductToCart(@RequestBody CartProduct cartProduct, @UserId Long userId) {
        cartService.addProduct(cartProduct, userId);
        return ApiResult.ok(null);
    }

    @PostMapping("/all")
    @LoginRequired
    public ApiResult<Void> updateAllProductsToCart(@RequestBody List<CartProduct> cartProducts, @UserId Long userId) {
        cartService.addProducts(cartProducts, userId);
        return ApiResult.ok(null);
    }

    @DeleteMapping("/{productId}")
    @LoginRequired
    public ApiResult<Void> deleteProductFromCart(@RequestParam Long productId, @UserId Long userId) {
        cartService.deleteProduct(productId, userId);
        return ApiResult.ok(null);
    }

    @GetMapping
    @LoginRequired
    public ApiResult<ShopBasket> getAllProductsFromCart(@UserId Long userId) {
        return ApiResult.ok(cartService.getProducts(userId));
    }

}
