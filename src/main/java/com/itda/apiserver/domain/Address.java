package com.itda.apiserver.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Address {

    @Column(name = "region_1depth_name")
    private String regionOneDepthName;

    @Column(name = "region_2depth_name")
    private String regionTwoDepthName;

    @Column(name = "region_3depth_name")
    private String regionThreeDepthName;

    private int mainBuildingNo;
    private int subBuildingNo;
    private int zoneNo;

}
