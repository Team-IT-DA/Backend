package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.*;
import com.itda.apiserver.service.ProductService;
import com.itda.apiserver.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

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

    @GetMapping("/{productId}")
    public ApiResult<DetailProductResponse> showDetailProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);
        return ApiResult.ok(new DetailProductResponse(getDetailProduct(product)));
    }

    @GetMapping("/{productId}/reviews")
    public ApiResult<ProductReviewsInfoDto> getProductReviews(@PathVariable Long productId, Pageable pageable) {
        return ApiResult.ok(reviewService.getProductReviews(productId, pageable));
    }

    private DetailProduct getDetailProduct(Product product) {

        User seller = product.getSeller();
        SellerDto sellerDto = new SellerDto(seller.getId(), seller.getName(), seller.getSellerImageUrl(), seller.getSellerDescription());

        return DetailProduct.builder()
                .id(product.getId())
                .name(product.getTitle())
                .supTitle(product.getDescription())
                .price(product.getPrice())
                .salesUnit(product.getSalesUnit())
                .capacity(product.getCapacity())
                .deliveryFee(product.getDeliveryFee())
                .deliveryFeeCondition(product.getDeliveryDescription())
                .origin(product.getOrigin())
                .packagingType(product.getPackageType())
                .description(product.getDescription())
                .imgUrl(product.getImageUrl())
                .seller(sellerDto)
                .build();
    }

    @GetMapping(params = "productName")
    public ApiResult<SearchProductDto> showProductsByName(@RequestParam String productName) {
        List<Product> products = productService.getProductsByName(productName);
        return ApiResult.ok(new SearchProductDto(getProductDtos(products)));
    }

    @GetMapping(params = "sellerName")
    public ApiResult<SearchProductDto> showProductsBySellerName(@RequestParam String sellerName) {
        List<Product> products = productService.getProductsBySellerName(sellerName);
        return ApiResult.ok(new SearchProductDto(getProductDtos(products)));
    }

    @GetMapping(params = "category")
    public ApiResult<SearchProductDto> showProductsByCategory(@RequestParam String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ApiResult.ok(new SearchProductDto(getProductDtos(products)));
    }

    private List<GetAllProductDto> getProductDtos(List<Product> products) {
        return products.stream()
                .map(product -> new GetAllProductDto(product.getId(), product.getImageUrl(), product.getTitle(),
                        product.getSeller().getName(), product.getPrice()))
                .collect(Collectors.toList());
    }
}
