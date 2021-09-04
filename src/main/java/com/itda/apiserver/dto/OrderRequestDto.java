package com.itda.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private List<OrderProductRequest> orderList;
    private int orderPrice;
    private int shippingFee;
    private int totalPrice;
    private Long shippingAddressId;

}
