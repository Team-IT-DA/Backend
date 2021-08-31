package com.itda.apiserver.service;

import com.itda.apiserver.redis.OrderValidation;
import com.itda.apiserver.repository.OrderValidationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderValidationServiceTest {

    @Autowired
    private OrderValidationService orderValidationService;

    @MockBean
    private OrderValidationRepository orderValidationRepository;

    @Mock
    private OrderValidation orderValidation;

    @Test
    @DisplayName("주문 중복 확인 기능 테스트")
    void duplicateOrder() {

        when(orderValidationRepository.findById(anyLong())).thenReturn(Optional.of(orderValidation));

        assertThat(orderValidationService.isDuplicatedOrder(1L)).isTrue();

    }
}
