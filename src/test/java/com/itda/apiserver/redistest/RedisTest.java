package com.itda.apiserver.redistest;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.Role;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.redis.ShopBascket;
import com.itda.apiserver.repository.ShopBascketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

@SpringBootTest
public class RedisTest {

    @Autowired
    ShopBascketRepository shopBascketRepository;

    @DisplayName("Redis 에 key-value 로 값을 넣어본다.")
    @Test
    public void addValue() {

        ShopBascket bascket = new ShopBascket("token");
        bascket.addButtonClick();

        shopBascketRepository.save(bascket);


        Optional<ShopBascket> token = shopBascketRepository.findById("token");
        Assertions.assertEquals(token.get().getProductCount(), 1);
    }

}
