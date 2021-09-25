package com.itda.apiserver.dto;

import com.itda.apiserver.domain.ReviewImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MyReviewsDto {

    private LocalDateTime reviewDate;
    private String productImage;
    private String productName;
    private String reviewImage;

    // 첫번째 이미지만 넣기 위해서 get(0) 을 활용했습니다.
    public MyReviewsDto(List<ReviewImage> reviewImage, String productImage, String productName, LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
        this.productImage = productImage;
        this.productName = productName;
        this.reviewImage = reviewImage.get(0).getUrl();
    }
}
