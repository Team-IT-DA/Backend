package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Mock
    AddproductRequestDto addProductDto;

    @DisplayName("판매자는 상품 추가를 할 수 있다.")
    @Test
    void addProducts() {
        productService.addProduct(addProductDto);
        verify(() -> Product.of(addProductDto), times(1));
        verify(productRepository, times(1)).save(any(Product.class));
    }

}