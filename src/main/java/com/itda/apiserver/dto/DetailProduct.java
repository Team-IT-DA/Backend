package com.itda.apiserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailProduct {

    private Long id;
    private String name;
    private String subTitle;
    private int price;
    private String salesUnit;
    private String capacity;
    private int deliveryFee;
    private String deliveryFeeCondition;
    private String origin;
    private String packagingType;
    private String description;
    private String imgUrl;
    private SellerDto seller;
}
