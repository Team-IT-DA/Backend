package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderSheetResponseDto {

    private Long orderSheetId;
    private LocalDate createdAt;
    private List<MyOrder> orderList;
}
