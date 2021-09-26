package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Core {

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private final List<ReviewImage> reviewImages = new ArrayList<>();

    private Review(String content, User user, Product product) {
        this.content = content;
        this.user = user;
        this.product = product;
    }

    public static Review createReview(String content, User user, Product product, List<ReviewImage> reviewImages) {
        Review review = new Review(content, user, product);
        review.addReviewImages(reviewImages);
        return review;
    }

    private void addReviewImages(List<ReviewImage> reviewImages) {
        this.reviewImages.addAll(reviewImages);
        reviewImages.stream()
                .forEach(reviewImage -> reviewImage.setReview(this));
    }

    public boolean isMyReview(Long userId) {
        return this.user.getId().equals(userId);
    }
}
