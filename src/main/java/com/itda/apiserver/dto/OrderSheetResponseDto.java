package com.itda.apiserver.dto;

import com.itda.apiserver.domain.OrderSheet;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class OrderSheetResponseDto {

    private Long orderSheetId;
    private LocalDate createdAt;
    private List<MyOrder> orderList;

    public OrderSheetResponseDto(OrderSheet orderSheet, List<MyOrder> myOrders) {
        orderSheetId = orderSheet.getId();
        createdAt = orderSheet.getCreatedAt().toLocalDate();
        orderList = myOrders;
    }
}
