package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/products/{productId}/review")
    @LoginRequired
    public ApiResult<Void> addReview(@PathVariable Long productId, @UserId Long userId,
                                     @RequestBody AddReviewRequestDto addReviewRequest) {
        reviewService.addReview(userId, productId, addReviewRequest);
        return ApiResult.ok(null);
    }
}
