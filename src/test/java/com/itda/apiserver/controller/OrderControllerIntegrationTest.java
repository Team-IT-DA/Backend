package com.itda.apiserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.ShippingInfo;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.OrderProductRequest;
import com.itda.apiserver.dto.OrderRequestDto;
import com.itda.apiserver.exception.OrderDuplicationException;
import com.itda.apiserver.jwt.TokenProvider;
import com.itda.apiserver.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.itda.apiserver.TestHelper.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerIntegrationTest {

    @Autowired
    private EntityManager em;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() throws InterruptedException {

        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    @DisplayName("주문 기능 통합 테스트")
    void order() throws Exception {

        User user = returnUserEntity();
        em.persist(user);

        String token = "Bearer " + tokenProvider.createToken(user.getId());

        mockMvc.perform(post("/api/order")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequestDto(user))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderList").exists())
                .andExpect(jsonPath("$.data.totalPrice").exists())
                .andDo(print());
    }

    public OrderRequestDto createOrderRequestDto(User user) {

        ShippingInfo shippingInfo = createShippingInfo(user);
        em.persist(shippingInfo);

        Product product = createProduct();
        em.persist(product);

        List<OrderProductRequest> orderProductList = new ArrayList<>();
        OrderProductRequest orderProduct1 = new OrderProductRequest();
        orderProduct1.setProductId(product.getId());
        orderProduct1.setCount(1);
        orderProductList.add(orderProduct1);

        OrderProductRequest orderProduct2 = new OrderProductRequest();
        orderProduct2.setProductId(product.getId());
        orderProduct2.setCount(2);
        orderProductList.add(orderProduct2);

        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setShippingAddressId(shippingInfo.getId());
        orderRequest.setOrderList(orderProductList);
        orderRequest.setOrderPrice(30000);
        orderRequest.setTotalPrice(36000);
        orderRequest.setShippingFee(6000);

        return orderRequest;
    }

    @Test
    @DisplayName("주문 중복 요청 시 OrderDuplication 예외가 발생한다.")
    void duplicatedOrder() throws Exception {

        User user = returnUserEntity();
        em.persist(user);

        String token = "Bearer " + tokenProvider.createToken(user.getId());

        mockMvc.perform(post("/api/order")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequestDto(user))))
                .andExpect(status().isOk());

        assertThatThrownBy(() ->
                mockMvc.perform(post("/api/order")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequestDto(user))))
        ).hasCause(new OrderDuplicationException());
    }

    @Test
    @DisplayName("마이페이지 주문 조회 기능 테스트")
    void showMyOrders() throws Exception {

        User user = returnUserEntity();
        em.persist(user);

        String token = "Bearer " + tokenProvider.createToken(user.getId());

        orderService.order(user.getId(), createOrderRequestDto(user));

        mockMvc.perform(get("/api/myPage/orders")
                .header("Authorization", token)
                .queryParam("page", "0")
                .queryParam("size", "5")
                .queryParam("sort", "createdAt, desc")
                .queryParam("period", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderSheetList[*].orderSheetId").exists())
                .andExpect(jsonPath("$.data.orderSheetList[*].createdAt").exists())
                .andExpect(jsonPath("$.data.orderSheetList[*].orderList").isArray())
                .andExpect(jsonPath("$.data.orderSheetList[*].orderList[*].productName").exists())
                .andExpect(jsonPath("$.data.orderSheetList[*].orderList[*].hasReview").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("마이페이지 주문 조회 기능 페이지네이션 테스트")
    void showMyOrdersPagination() throws Exception {

        User user = returnUserEntity();
        em.persist(user);

        String token = "Bearer " + tokenProvider.createToken(user.getId());

        for (int i = 0; i < 5; i++) {
            orderService.order(user.getId(), createOrderRequestDto(user));
            TimeUnit.SECONDS.sleep(1);
        }

        mockMvc.perform(get("/api/myPage/orders")
                .header("Authorization", token)
                .queryParam("page", "0")
                .queryParam("size", "3")
                .queryParam("sort", "createdAt, desc")
                .queryParam("period", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.orderSheetList", hasSize(3)))
                .andDo(print());
    }

}
