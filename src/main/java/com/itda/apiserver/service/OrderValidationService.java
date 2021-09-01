package com.itda.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderValidationService {

    private static final String REDIS_KEY = "order";

    private final StringRedisTemplate stringRedisTemplate;


    public boolean isDuplicatedOrder() {
        return stringRedisTemplate.opsForValue().get(REDIS_KEY) != null;
    }

    public void save(Long userId) {
        stringRedisTemplate.opsForValue().set(REDIS_KEY, String.valueOf(userId), 1, TimeUnit.SECONDS);
    }
}
