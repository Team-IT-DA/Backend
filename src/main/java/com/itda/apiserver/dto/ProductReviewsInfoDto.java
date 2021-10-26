package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductReviewsInfoDto {
    private Long totalCounts;
    private List<ReviewsOfProductDto> reviewsOfProductDtos;
}

