package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.MyReviewsDto;
import com.itda.apiserver.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/products/{productId}/reviews")
    @LoginRequired
    public ApiResult<Void> addReview(@PathVariable Long productId, @UserId Long userId,
                                     @RequestBody AddReviewRequestDto addReviewRequest) {
        reviewService.addReview(userId, productId, addReviewRequest);
        return ApiResult.ok(null);
    }

    @GetMapping("/api/myPage/reviews")
    @LoginRequired
    public ApiResult<List<MyReviewsDto>> getMyReviews(@UserId Long userId) {
        return ApiResult.ok(reviewService.getMyReviews(userId));
    }
}
