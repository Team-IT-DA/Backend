package com.itda.apiserver.redistest;

import com.itda.apiserver.redis.OrderValidation;
import com.itda.apiserver.repository.OrderValidationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderValidationTest {

    @Autowired
    private OrderValidationRepository orderValidationRepository;

    @Test
    @DisplayName("레디스 order 키의 TTL은 1초이다")
    void order_redis() throws InterruptedException {
        Long userId = 1L;
        orderValidationRepository.save(new OrderValidation(userId));

        assertThat(orderValidationRepository.findById(userId)).isNotEmpty();

        TimeUnit.SECONDS.sleep(2);

        assertThat(orderValidationRepository.findById(userId)).isEmpty();
    }
}
