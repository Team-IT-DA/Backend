package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.dto.CartProduct;
import com.itda.apiserver.exception.NotFoundProductFromCartException;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.ShopBasketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ShopBasketRepository shopBasketRepository;

    @MockBean
    private ProductRepository productRepository;

    @Mock
    private CartProduct cartRequestDto;

    @MockBean
    private ShopBasket shopBasket;

    @MockBean
    private Product product;

    @Test
    @DisplayName("장바구니에 품목을 추가합니다.")
    void addProduct() {

        when(cartRequestDto.getId()).thenReturn(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        cartService.addProduct(cartRequestDto, 1L);

        verify(shopBasketRepository, times(1)).save(any(ShopBasket.class));
    }

    @Test
    @DisplayName("장바구니에 품목을 삭제합니다.")
    void dropProduct() {
        when(cartRequestDto.getId()).thenReturn(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(shopBasketRepository.findById(1L)).thenReturn(Optional.of(shopBasket));

        cartService.deleteProduct(cartRequestDto.getId(), 1L);

        verify(shopBasketRepository, times(1)).save(any(ShopBasket.class));
    }

    @Test
    @DisplayName("삭제 시 장바구니에 품목이 없다면 NotFoundProductFromCartException 을 Return 합니다.")
    void returnExceptionIfNotExistProductFromCart() {
        when(cartRequestDto.getId()).thenReturn(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Assertions.assertThrows(
                NotFoundProductFromCartException.class,
                () -> cartService.deleteProduct(cartRequestDto.getId(), 1L)
        );
    }

    @Test
    @DisplayName("장바구니에 품목을 가져옵니다.")
    void getProducts() {

        when(shopBasketRepository.findById(1L)).thenReturn(Optional.of(shopBasket));

        cartService.getProducts(1L);

        verify(shopBasketRepository, times(1)).findById(anyLong());
    }
}
