package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.dto.OrderProductRequest;
import com.itda.apiserver.dto.OrderRequestDto;
import com.itda.apiserver.exception.OrderDuplicationException;
import com.itda.apiserver.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TokenProvider tokenProvider;

    private OrderRequestDto orderRequest;
    private String token;

    @BeforeEach
    void setUp() throws InterruptedException {
        orderRequest = getOrderRequest();
        token = "Bearer " + tokenProvider.createToken(1L);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    @DisplayName("주문 기능 통합 테스트")
    void order() throws Exception {

        mockMvc.perform(post("/api/order")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderList").exists())
                .andExpect(jsonPath("$.data.totalPrice").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("주문 중복 요청 시 OrderDuplication 예외가 발생한다.")
    void duplicatedOrder() throws Exception {

        mockMvc.perform(post("/api/order")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk());

        assertThatThrownBy(() ->
                mockMvc.perform(post("/api/order")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
        ).hasCause(new OrderDuplicationException());
    }

    private OrderRequestDto getOrderRequest() {

        List<OrderProductRequest> orderProductList = new ArrayList<>();
        OrderProductRequest orderProduct1 = new OrderProductRequest();
        orderProduct1.setProductId(43L);
        orderProduct1.setCount(1);
        orderProductList.add(orderProduct1);

        OrderProductRequest orderProduct2 = new OrderProductRequest();
        orderProduct2.setProductId(44L);
        orderProduct2.setCount(2);
        orderProductList.add(orderProduct2);

        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setShippingAddressId(4L);
        orderRequest.setOrderList(orderProductList);
        orderRequest.setOrderPrice(150000);
        orderRequest.setTotalPrice(156000);
        orderRequest.setShippingFee(6000);

        return orderRequest;
    }

}
