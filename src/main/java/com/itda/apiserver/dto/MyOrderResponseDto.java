package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyOrderResponseDto {

    private List<MyOrder> orderList;
}
