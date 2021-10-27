package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllProductDto {

    private Long id;
    private String imageUrl;
    private String description;
    private String productName;
    private String sellerName;
    private int price;

}
