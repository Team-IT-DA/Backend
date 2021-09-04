package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.domain.Order;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.dto.OrderProductResponse;
import com.itda.apiserver.dto.OrderRequestDto;
import com.itda.apiserver.dto.OrderResponseDto;
import com.itda.apiserver.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @LoginRequired
    @PostMapping("/api/order")
    public ApiResult<OrderResponseDto> order(@UserId Long userId, @RequestBody OrderRequestDto request) {
        List<Order> orders = orderService.order(userId, request);
        return ApiResult.ok(getOrderResponse(orders));
    }

    private OrderResponseDto getOrderResponse(List<Order> orders) {

        int totalPrice = orders.stream()
                .mapToInt(Order::getPricePerOrder)
                .sum();

        List<OrderProductResponse> orderProductResponses = orders.stream()
                .map(order -> {
                    Product product = order.getProduct();
                    OrderProductResponse response =
                            new OrderProductResponse(product.getTitle(), product.getId(), product.getPrice(), product.getDeliveryFee(),
                                    order.getQuantity(), product.getBank(), product.getAccountHolder(), product.getAccount());
                    return response;
                }).collect(Collectors.toList());

        return new OrderResponseDto(orderProductResponses, totalPrice);
    }

}
