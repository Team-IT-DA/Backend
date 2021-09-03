package com.itda.apiserver.redistest;

import com.itda.apiserver.redis.BasketProduct;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ShopBasketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RedisTest {

    @Autowired
    ShopBasketRepository shopBasketRepository;

    @DisplayName("Redis 에 key-value 로 값을 넣어본다.")
    @Test
    public void addValue() {


        BasketProduct basketProduct = new BasketProduct(1L, "yeon 에어 맥북", 10000, 3000, "http://www.naver.com", 0, "roach");
        BasketProduct basketProduct1 = new BasketProduct(2L, "Honux 의 건담", 9000, 2000, "http://www.naver.com", 1, "roach");

        ShopBasket bascket = new ShopBasket(1L);

        bascket.addProduct(basketProduct);
        bascket.addProduct(basketProduct1);

        bascket.getProduct(basketProduct.getProductId()).addButtonClick();

        shopBasketRepository.save(bascket);


        Optional<ShopBasket> token = shopBasketRepository.findById(1L);
        Assertions.assertEquals(token.get().getProduct(basketProduct.getProductId()).getProductCount(), 1);
        Assertions.assertEquals(token.get().getProduct(basketProduct1.getProductId()).getProductName(), "Honux 의 건담");
    }

}
