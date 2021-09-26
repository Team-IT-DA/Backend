package com.itda.apiserver.dto;

import com.itda.apiserver.domain.Order;
import lombok.Getter;

@Getter
public class OrderProductResponse {

    private String productName;
    private Long productId;
    private int price;
    private int shippingFee;
    private int count;
    private String bank;
    private String accountHolder;
    private String account;

    public OrderProductResponse(Order order) {
        productName = order.getProduct().getTitle();
        productId = order.getProduct().getId();
        price = order.getProduct().getPrice();
        shippingFee = order.getProduct().getDeliveryFee();
        count = order.getQuantity();
        bank = order.getProduct().getBank();
        accountHolder = order.getProduct().getAccountHolder();
        account = order.getProduct().getAccount();
    }
}
