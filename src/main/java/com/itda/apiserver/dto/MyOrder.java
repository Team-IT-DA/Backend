package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyOrder {

    private String productName;
    private Long productId;
    private int price;
    private int shippingFee;
    private int count;
    private String bank;
    private String accountHolder;
    private String account;
}
