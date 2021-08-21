package com.itda.apiserver.redistest;

import com.itda.apiserver.redis.BascketProduct;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ShopBascketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RedisTest {

    @Autowired
    ShopBascketRepository shopBascketRepository;

    @DisplayName("Redis 에 key-value 로 값을 넣어본다.")
    @Test
    public void addValue() {


        BascketProduct bascketProduct = new BascketProduct(1L, "yeon 에어 맥북", "http://www.naver.com", 10000);
        BascketProduct bascketProduct1 = new BascketProduct(2L, "Honux 의 건담", "http://www.naver.com", 9000);

        ShopBasket bascket = new ShopBasket(1L);
        bascket.addProduct(bascketProduct);
        bascket.addProduct(bascketProduct1);
        bascket.getProduct(0).addButtonClick();

        shopBascketRepository.save(bascket);


        Optional<ShopBasket> token = shopBascketRepository.findById(1L);
        Assertions.assertEquals(token.get().getProduct(0).getProductCount(), 1);
        Assertions.assertEquals(token.get().getProduct(1).getProductName(), "Honux 의 건담");
    }

}
