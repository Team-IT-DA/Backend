package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponseDto {

    private List<OrderProductResponse> orderList;
    private int totalPrice;

}
