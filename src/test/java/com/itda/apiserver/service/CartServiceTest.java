package com.itda.apiserver.service;

import com.itda.apiserver.dto.CartProduct;
import com.itda.apiserver.dto.CartRequestDto;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ShopBascketRepository;
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
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ShopBascketRepository shopBascketRepository;

    @Mock
    private CartRequestDto cartRequestDto;

    @Mock
    private List<CartProduct> cartProducts;

    @MockBean
    private ShopBasket shopBasket;

    @Test
    @DisplayName("장바구니에 품목을 추가합니다.")
    void addProduct() {

        when(cartRequestDto.getProducts()).thenReturn(cartProducts);

        cartService.addProduct(cartRequestDto, 1L);

        verify(shopBascketRepository, times(1)).save(any(ShopBasket.class));
    }

    @Test
    @DisplayName("장바구니에 품목을 가져옵니다.")
    void getProducts() {

        when(shopBascketRepository.findById(1L)).thenReturn(Optional.of(shopBasket));

        cartService.getProducts(1L);

        verify(shopBascketRepository, times(1)).findById(anyLong());
    }
}