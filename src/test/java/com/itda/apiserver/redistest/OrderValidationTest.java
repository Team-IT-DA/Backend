package com.itda.apiserver.redistest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderValidationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    @DisplayName("레디스 order 키의 TTL은 1초이다")
    void order_redis() throws InterruptedException {
        Long userId = 1L;
        String redisKey = "order";

        stringRedisTemplate.opsForValue().set(redisKey, String.valueOf(userId), 1, TimeUnit.SECONDS);

        assertThat(stringRedisTemplate.opsForValue().get(redisKey)).isNotEmpty();

        TimeUnit.SECONDS.sleep(2);

        assertThat(stringRedisTemplate.opsForValue().get(redisKey)).isNull();
    }
}
