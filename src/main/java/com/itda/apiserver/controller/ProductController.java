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

    /**
     * E2E Testing 을 위해서 현재는 Long userId 를 붙여둠
     * 추후 인터셉터를 통해서 이를 교체할 예정
     * @param addproductRequestDto
     * @param userId
     */
    @PostMapping
    public ApiResult<Void> addProduct(@RequestBody AddproductRequestDto addproductRequestDto, @RequestParam Long userId) {
        productService.addProduct(addproductRequestDto, userId);
        return ApiResult.ok(null);
    }

}
