package com.itda.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductRequest {

    private Long productId;
    private int count;
}
