package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_1depth_name")
    private String regionOneDepthName;

    @Column(name = "region_2depth_name")
    private String regionTwoDepthName;

    @Column(name = "region_3depth_name")
    private String regionThreeDepthName;

    private String roadName;
    private Boolean mountainYn;
    private Boolean undergroundYn;
    private Integer mainBuildingNo;
    private Integer subBuildingNo;
    private Integer zoneNo;
    private String latitude;
    private String longitude;
}
