package com.itda.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowSellerProfileDto {

    private boolean isSeller;
    private String sellerDescription;
    private String sellerImageUrl;
}
