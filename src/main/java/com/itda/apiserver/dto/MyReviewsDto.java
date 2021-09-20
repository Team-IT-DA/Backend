package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MyReviewsDto {

    private LocalDateTime reviewDate;
    private String productImage;
    private String productName;
    private String ReviewImage;

    // 첫번째 이미지만 넣기 위해서 get(0) 을 활용했습니다.
    public MyReviewsDto(LocalDateTime reviewDate, String productImage, String productName, List<String> reviewImage) {
        this.reviewDate = reviewDate;
        this.productImage = productImage;
        this.productName = productName;
        ReviewImage = reviewImage.get(0);
    }
}
