package com.itda.apiserver.service;

import com.itda.apiserver.domain.*;
import com.itda.apiserver.dto.OrderProductRequest;
import com.itda.apiserver.dto.OrderProductResponse;
import com.itda.apiserver.dto.OrderRequestDto;
import com.itda.apiserver.dto.OrderResponseDto;
import com.itda.apiserver.exception.OrderDuplicationException;
import com.itda.apiserver.exception.ProductNotFountException;
import com.itda.apiserver.exception.UserNotFoundException;
import com.itda.apiserver.repository.OrderHistoryRepository;
import com.itda.apiserver.repository.OrderSheetRepository;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderValidationService orderValidationService;

    /**
     * Order: 제품 하나당 주문 내역
     * OrderSheet: 유저가 주문한 주문 내역
     */
    @Transactional
    public OrderResponseDto order(Long userId, OrderRequestDto orderRequest) {

        if (orderValidationService.isDuplicatedOrder()) {
            throw new OrderDuplicationException();
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Address address = Address.builder()
                .regionOneDepthName(orderRequest.getShippingAddress().getRegionOneDepthName())
                .regionTwoDepthName(orderRequest.getShippingAddress().getRegionTwoDepthName())
                .regionThreeDepthName(orderRequest.getShippingAddress().getRegionThreeDepthName())
                .mainBuildingNo(orderRequest.getShippingAddress().getMainBuildingNo())
                .subBuildingNo(orderRequest.getShippingAddress().getSubBuildingNo())
                .zoneNo(orderRequest.getShippingAddress().getZoneNo())
                .build();

        ShippingInfo shippingInfo = new ShippingInfo(address, user, orderRequest.getShippingAddress().getConsignee(),
                orderRequest.getShippingAddress().getMessage(), orderRequest.getShippingAddress().getPhone(),
                orderRequest.getShippingAddress().isDefaultAddrYn());

        List<Order> orders = getOrders(orderRequest.getOrderList());

        OrderSheet orderSheet = OrderSheet.createOrderSheet(orderRequest.getTotalPrice(), user, shippingInfo, orders);
        orderSheetRepository.save(orderSheet);

        OrderHistory orderHistory = new OrderHistory(orderSheet, user);
        orderHistoryRepository.save(orderHistory);

        orderValidationService.save(userId);

        return getOrderResponse(orders);
    }

    private List<Order> getOrders(List<OrderProductRequest> orderList) {

        return orderList.stream()
                .map(orderProductDto -> {
                    Product product = productRepository.findById(orderProductDto.getProductId()).orElseThrow(ProductNotFountException::new);
                    Order order = new Order(orderProductDto.getCount(), product);
                    return order;
                }).collect(Collectors.toList());
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
