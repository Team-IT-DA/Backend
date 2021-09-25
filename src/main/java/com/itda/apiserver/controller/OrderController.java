package com.itda.apiserver.controller;

import com.itda.apiserver.annotation.LoginRequired;
import com.itda.apiserver.annotation.UserId;
import com.itda.apiserver.domain.Order;
import com.itda.apiserver.domain.OrderSheet;
import com.itda.apiserver.dto.*;
import com.itda.apiserver.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
                .map(OrderProductResponse::new)
                .collect(Collectors.toList());

        return new OrderResponseDto(orderProductResponses, totalPrice);
    }

    @LoginRequired
    @GetMapping("/api/myPage/orders")
    public ApiResult<MyOrderResponseDto> showOrders(@UserId Long userId, @RequestParam(required = false) Integer period, Pageable pageable) {
        List<OrderSheet> orderSheet = orderService.getOrderSheet(userId, period, pageable);
        return ApiResult.ok(getMyOrderResponse(userId, orderSheet));
    }

    private MyOrderResponseDto getMyOrderResponse(Long userId, List<OrderSheet> orderSheets) {

        List<OrderSheetResponseDto> orderSheetResponseDtos = orderSheets.stream()
                .map(orderSheet -> {
                    List<MyOrder> myOrders = orderSheet.getOrders().stream()
                            .map(order -> new MyOrder(order, order.getProduct().hasMyReview(userId)))
                            .collect(Collectors.toList());

                    return new OrderSheetResponseDto(orderSheet, myOrders);
                }).collect(Collectors.toList());

        return new MyOrderResponseDto(orderSheetResponseDtos);
    }

}
