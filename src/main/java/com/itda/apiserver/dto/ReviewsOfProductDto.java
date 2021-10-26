package com.itda.apiserver.dto;

import com.itda.apiserver.domain.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewsOfProductDto {
    private Long id;
    private String name;
    private String imgURL;
    private LocalDateTime date;
    private String contents;
    private List<ReviewImage> reviewImages;
}
