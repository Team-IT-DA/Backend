package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.dto.*;
import com.itda.apiserver.jwt.TokenExtractor;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(value = {TokenProvider.class, TokenExtractor.class})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void order() throws Exception {

        Long userId = 1L;
        OrderRequestDto orderRequest = getOrderRequest();
        String token = "Bearer " + tokenProvider.createToken(userId);

        when(orderService.order(anyLong(), any(OrderRequestDto.class))).thenReturn(getOrderResponse());

        mockMvc.perform(post("/api/order")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderList").exists())
                .andExpect(jsonPath("$.data.totalPrice").exists())
                .andDo(print());
    }

    private OrderRequestDto getOrderRequest() {
        ShippingInfoDto shippingInfoDto = new ShippingInfoDto();
        shippingInfoDto.setRegionOneDepthName("서울특별시");
        shippingInfoDto.setRegionTwoDepthName("강남구");
        shippingInfoDto.setRegionThreeDepthName("역삼동");
        shippingInfoDto.setPhone("01011112222");
        shippingInfoDto.setConsignee("호눅스");
        shippingInfoDto.setMessage("집 앞에 두고 가세요");
        shippingInfoDto.setZoneNo(12345);
        shippingInfoDto.setMainBuildingNo(40);
        shippingInfoDto.setSubBuildingNo(4);
        shippingInfoDto.setDefaultAddrYn(true);

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
        orderRequest.setShippingAddress(shippingInfoDto);
        orderRequest.setOrderList(orderProductList);
        orderRequest.setOrderPrice(150000);
        orderRequest.setTotalPrice(156000);
        orderRequest.setShippingFee(6000);

        return orderRequest;
    }

    private OrderResponseDto getOrderResponse() {

        List<OrderProductResponse> orderProductResponseList = new ArrayList<>();

        orderProductResponseList.add(
                new OrderProductResponse("사과", 1L, 10000, 3000, 3, "우리", "yeon", "123-123-1234")
        );

        return new OrderResponseDto(orderProductResponseList, 33000);
    }
}
