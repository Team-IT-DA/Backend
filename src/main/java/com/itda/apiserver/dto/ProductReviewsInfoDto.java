package com.itda.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductReviewsInfoDto {
    private int totalCounts;
    private List<ReviewsOfProductDto> reviewsOfProductDtos;
}

