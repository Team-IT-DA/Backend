package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.GetAllProductDto;
import com.itda.apiserver.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResult<List<GetAllProductDto>> getProduct(Pageable pageable) {
        return ApiResult.ok(productService.getProducts(pageable));
    }

    @PostMapping
    @LoginRequired
    public ApiResult<Void> addProduct(@RequestBody AddproductRequestDto addproductRequestDto, @UserId Long userId) {
        productService.addProduct(addproductRequestDto, userId);
        return ApiResult.ok(null);
    }

    @PostMapping("/{productId}/review")
    @LoginRequired
    public ApiResult<Void> addReview(@PathVariable Long productId, @UserId Long userId,
                                     @RequestBody AddReviewRequestDto addReviewRequest) {
        productService.addReview(userId, productId, addReviewRequest);
        return ApiResult.ok(null);
    }
}
