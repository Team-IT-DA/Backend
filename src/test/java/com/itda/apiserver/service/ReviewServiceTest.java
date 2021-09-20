package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Review;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddReviewRequestDto;
import com.itda.apiserver.exception.ProductNotFountException;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.ReviewRepository;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ReviewRepository reviewRepository;

    @Mock
    AddReviewRequestDto addReviewDto;

    @Mock
    User user;

    @Mock
    Product product;

    @Test
    @DisplayName("리뷰 추가 기능 테스트")
    void addReview() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        reviewService.addReview(1L, 1L, addReviewDto);

        verify(userRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 추가 시 존재하지 않는 유저의 id가 들어온 경우 UserNotFoundException 발생")
    void addReviewInvalidUserId() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        assertThrows(UserNotFoundException.class, () -> reviewService.addReview(1L, 1L, addReviewDto));

        verify(userRepository, times(1)).findById(anyLong());
        verify(productRepository, times(0)).findById(anyLong());
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 추가 시 존재하지 않는 제품의 id가 들어온 경우 ProductNotFoundException 발생")
    void addReviewInvalidProductId() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFountException.class, () -> reviewService.addReview(1L, 1L, addReviewDto));

        verify(userRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).findById(anyLong());
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

}
