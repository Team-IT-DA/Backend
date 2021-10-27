package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.*;
import com.itda.apiserver.service.ProductService;
import com.itda.apiserver.service.ReviewService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "제품 전체 조회")
    public ApiResult<List<GetAllProductDto>> getProduct(Pageable pageable) {
        return ApiResult.ok(productService.getProducts(pageable));
    }

    @PostMapping
    @LoginRequired
    @ApiOperation(value = "제품 추가")
    public ApiResult<Void> addProduct(@RequestBody AddproductRequestDto addproductRequestDto, @UserId Long userId) {
        productService.addProduct(addproductRequestDto, userId);
        return ApiResult.ok(null);
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "제품 상세 조회")
    public ApiResult<DetailProductResponse> showDetailProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);
        return ApiResult.ok(new DetailProductResponse(getDetailProduct(product)));
    }

    @GetMapping("/{productId}/reviews")
    @ApiOperation(value = "제품 별 리뷰 조회")
    public ApiResult<ProductReviewsInfoDto> getProductReviews(@PathVariable Long productId, Pageable pageable) {
        return ApiResult.ok(reviewService.getProductReviews(productId, pageable));
    }

    private DetailProduct getDetailProduct(Product product) {

        User seller = product.getSeller();
        SellerDto sellerDto = new SellerDto(seller.getId(), seller.getName(), seller.getSellerImageUrl(), seller.getSellerDescription());

        return DetailProduct.builder()
                .id(product.getId())
                .name(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .salesUnit(product.getSalesUnit())
                .weight(product.getCapacity())
                .deliveryFee(product.getDeliveryFee())
                .deliveryFeeCondition(product.getDeliveryDescription())
                .origin(product.getOrigin())
                .packagingType(product.getPackageType())
                .detailDescription(product.getDescription())
                .imgUrl(product.getImageUrl())
                .seller(sellerDto)
                .build();
    }

    @GetMapping(params = "productName")
    @ApiOperation(value = "제품 명으로 조회")
    public ApiResult<SearchProductDto> showProductsByName(@RequestParam String productName) {
        List<Product> products = productService.getProductsByName(productName);
        return ApiResult.ok(new SearchProductDto(getProductDtos(products)));
    }

    @GetMapping(params = "sellerName")
    @ApiOperation(value = "판매자 명으로 조회")
    public ApiResult<SearchProductDto> showProductsBySellerName(@RequestParam String sellerName) {
        List<Product> products = productService.getProductsBySellerName(sellerName);
        return ApiResult.ok(new SearchProductDto(getProductDtos(products)));
    }

    @GetMapping(params = "category")
    @ApiOperation(value = "카테고리 명으로 조회")
    public ApiResult<SearchProductDto> showProductsByCategory(@RequestParam String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ApiResult.ok(new SearchProductDto(getProductDtos(products)));
    }

    private List<GetAllProductDto> getProductDtos(List<Product> products) {
        return products.stream()
                .map(product -> new GetAllProductDto(product.getId(), product.getImageUrl(), product.getDescription(), product.getTitle(),
                        product.getSeller().getName(), product.getPrice()))
                .collect(Collectors.toList());
    }
}
