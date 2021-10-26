package com.itda.apiserver.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShippingAddressDto {

    private Long id;
    private String consignee;
    private String phone;
    private String regionOndDepthName;
    private String regionTwoDepthName;
    private String regionThreeDepthName;
    private int mainBuildingNo;
    private int subBuildingNo;
    private int zoneNo;
    private boolean defaultAddrYn;
    private String message;

}
