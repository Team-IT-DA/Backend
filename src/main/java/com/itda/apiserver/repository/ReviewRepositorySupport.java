package com.itda.apiserver.repository;

import com.itda.apiserver.dto.MyReviewsDto;

import java.util.List;

public interface ReviewRepositorySupport {

    List<MyReviewsDto> findUserReviews(Long userId);

}
