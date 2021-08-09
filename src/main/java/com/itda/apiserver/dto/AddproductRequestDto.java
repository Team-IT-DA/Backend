package com.itda.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddproductRequestDto {

    private String name;
    private String subTitle;
    private Integer price;
    private String salesUnit;
    private String productImage;
    private String capacity;
    private Integer deliveryFee;
    private String deliveryFeeCondition = "";
    private String origin;
    private String packagingType;
    private String notice;
    private String description;
    private String bank;
    private String accountHolder;
    private String account;
    private Long mainCategoryId;

}
