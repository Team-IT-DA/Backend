package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "review_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage extends Core {

    private String url;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public ReviewImage(String url) {
        this.url = url;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
