package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends Core {

    private String imageUrl;
    private String title;
    private String subTitle;
    private Integer price;
    private Integer deliveryFee;
    private String deliveryDescription;
    private String salesUnit;
    private String capacity;
    private String origin;
    private String packageType;
    private String notice;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    @OneToMany(mappedBy = "product")
    private final List<Review> reviews = new ArrayList<>();

}
