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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Mock
    AddproductRequestDto addProductDto;

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

    @DisplayName("상품 조회는 원하는 사이즈로 페이지 네이션이 된다.")
    @Test
    void getProducts() {
        Pageable pageable = createPageable();
        productService.getProducts(pageable);
        verify(productRepository, times(1)).findAll(pageable);
    }

    private Pageable createPageable() {
        return new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 11;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }

}