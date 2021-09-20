package com.itda.apiserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailProduct {

    private Long id;
    private String name;
    private String description;
    private int price;
    private String salesUnit;
    private String weight;
    private int deliveryFee;
    private String deliveryFeeCondition;
    private String origin;
    private String packagingType;
    private String detailDescription;
    private String imgUrl;
    private SellerDto seller;
}
