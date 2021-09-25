package com.itda.apiserver.dto;

import com.itda.apiserver.domain.Order;
import lombok.Getter;

@Getter
public class MyOrder {

    private String productName;
    private Long productId;
    private String productImgUrl;
    private int price;
    private int shippingFee;
    private int count;
    private String bank;
    private String accountHolder;
    private String account;
    private boolean hasReview;

    public MyOrder(Order order, boolean hasReview) {
        productName = order.getProduct().getTitle();
        productId = order.getProduct().getId();
        productImgUrl = order.getProduct().getImageUrl();
        price = order.getProduct().getPrice();
        shippingFee = order.getProduct().getDeliveryFee();
        count = order.getQuantity();
        bank = order.getProduct().getBank();
        accountHolder = order.getProduct().getAccountHolder();
        account = order.getProduct().getAccount();
        this.hasReview = hasReview;
    }
}
