package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private String bank;
    private String accountHolder;
    private String account;

    @Builder
    public Product(String imageUrl, String title, String subTitle, Integer price, Integer deliveryFee, String deliveryDescription, String salesUnit, String capacity, String origin, String packageType, String notice, String bank, String accountHolder, String account, String description, MainCategory mainCategory, User seller) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.subTitle = subTitle;
        this.price = price;
        this.deliveryFee = deliveryFee;
        this.deliveryDescription = deliveryDescription;
        this.salesUnit = salesUnit;
        this.capacity = capacity;
        this.origin = origin;
        this.packageType = packageType;
        this.notice = notice;
        this.bank = bank;
        this.accountHolder = accountHolder;
        this.account = account;
        this.description = description;
        this.mainCategory = mainCategory;
        this.seller = seller;
    }

    @Lob
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller")
    private User seller;

    @OneToMany(mappedBy = "product")
    private final List<Review> reviews = new ArrayList<>();

}
