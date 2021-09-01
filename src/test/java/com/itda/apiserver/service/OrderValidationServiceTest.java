package com.itda.apiserver.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderValidationServiceTest {

    @Autowired
    private OrderValidationService orderValidationService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    @DisplayName("주문 중복 확인 기능 테스트")
    void duplicateOrder() {

        stringRedisTemplate.opsForValue().set("order", String.valueOf(1L), 1, TimeUnit.SECONDS);

        assertThat(orderValidationService.isDuplicatedOrder()).isTrue();

    }
}
