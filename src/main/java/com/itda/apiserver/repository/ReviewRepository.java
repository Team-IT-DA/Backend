package com.itda.apiserver.repository;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Review;
import com.itda.apiserver.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUser(User user);

    Long countByProduct(Product product);

    List<Review> findAllByProduct(Product product, Pageable pageable);
}
