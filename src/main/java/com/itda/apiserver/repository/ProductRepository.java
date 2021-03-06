package com.itda.apiserver.repository;

import com.itda.apiserver.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p join fetch p.seller s where p.title like %?1%")
    List<Product> findByTitle(String title);

    @Query("select p from Product p join fetch p.seller s where s.name like %?1%")
    List<Product> findBySellerName(String sellerName);

    @Query("select p from Product p join fetch p.seller s join fetch p.mainCategory mc where mc.name like ?1")
    List<Product> findByCategory(String category);
}
