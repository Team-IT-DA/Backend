package com.itda.apiserver.service;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.repository.MainCategoryRepository;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    MainCategoryRepository mainCategoryRepository;

    @Mock
    User user;

    @Mock
    MainCategory mainCategory;

    @MockBean
    Product product;

    @Mock
    AddproductRequestDto addProductDto;

    @Mock
    List<Product> productList;


    @DisplayName("판매자는 상품 추가를 할 수 있다.")
    @Test
    void addProducts() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mainCategoryRepository.findById(anyLong())).thenReturn(Optional.of(mainCategory));

        productService.addProduct(addProductDto, 1L);

        verify(userRepository, times(1)).findById(1L);
        verify(mainCategoryRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("제품 상세 조회 기능 테스트")
    void getProduct() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productService.getProduct(1L);

        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("제품 이름으로 제품 검색 테스트")
    void getProductsByName() {

        when(productRepository.findByTitle(anyString())).thenReturn(productList);

        productService.getProductsByName("pizza");

        verify(productRepository, times(1)).findByTitle(anyString());
    }

}
