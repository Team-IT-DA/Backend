package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SellerDto {

    private Long id;
    private String name;
    private String imageUrl;
    private String description;

}
