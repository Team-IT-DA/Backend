package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Review;
import com.itda.apiserver.domain.ReviewImage;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.dto.MyReviewsDto;
import com.itda.apiserver.dto.ProductReviewsInfoDto;
import com.itda.apiserver.dto.ReviewsOfProductDto;
import com.itda.apiserver.exception.ProductNotFountException;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.ReviewRepository;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public void addReview(Long userId, Long productId, AddReviewRequestDto reviewRequest) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFountException::new);

        List<ReviewImage> reviewImages = reviewRequest.getImages().stream()
                .map(ReviewImage::new)
                .collect(Collectors.toList());

        Review review = Review.createReview(reviewRequest.getContents(), user, product, reviewImages);
        reviewRepository.save(review);
    }

    public List<MyReviewsDto> getMyReviews(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Review> userReviews = reviewRepository.findByUser(user);

        return userReviews.stream().map((review -> new MyReviewsDto(
                review.getReviewImages(),
                review.getProduct().getImageUrl(),
                review.getProduct().getTitle(),
                review.getCreatedAt()
        ))).collect(Collectors.toList());
    }

    public ProductReviewsInfoDto getProductReviews(Long productId, Pageable pageable) {

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFountException::new);
        Long totalCount = reviewRepository.countByProduct(product);
        List<ReviewsOfProductDto> reviewsOfProductDtos = null;

        if (totalCount > 0) {
            reviewsOfProductDtos = reviewRepository
                    .findAllByProduct(product, pageable)
                    .stream().map((review) -> {
                        return new ReviewsOfProductDto(
                                review.getId(),
                                review.getUser().getName(),
                                review.getUser().getSellerImageUrl(),
                                review.getCreatedAt(),
                                review.getContent(),
                                review.getReviewImages());
                    }).collect(Collectors.toList());
        }

        return new ProductReviewsInfoDto(totalCount, reviewsOfProductDtos);
    }
}
