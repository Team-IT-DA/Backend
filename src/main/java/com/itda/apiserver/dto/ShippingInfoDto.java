package com.itda.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingInfoDto {

    private String consignee;
    private String phone;
    private String regionOneDepthName;
    private String regionTwoDepthName;
    private String regionThreeDepthName;
    private int mainBuildingNo;
    private int subBuildingNo;
    private int zoneNo;
    private boolean defaultAddrYn;
    private String message;

}

