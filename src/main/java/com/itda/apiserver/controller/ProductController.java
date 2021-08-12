package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @LoginRequired
    public ApiResult<Void> addProduct(@RequestBody AddproductRequestDto addproductRequestDto, @UserId Long userId) {
        productService.addProduct(addproductRequestDto, userId);
        return ApiResult.ok(null);
    }

}
