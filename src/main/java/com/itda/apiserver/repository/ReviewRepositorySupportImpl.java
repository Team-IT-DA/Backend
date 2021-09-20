package com.itda.apiserver.repository;

import com.itda.apiserver.dto.MyReviewsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.itda.apiserver.domain.QReview.review;
import static com.itda.apiserver.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ReviewRepositorySupportImpl implements ReviewRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MyReviewsDto> findUserReviews(Long userId) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        MyReviewsDto.class,
                        review.reviewImages,
                        product.imageUrl,
                        product.title,
                        review.createdAt
                ))
                .from(review)
                .join(review.product, product)
                .where(review.user.id.eq(userId))
                .fetch();
    }
}
