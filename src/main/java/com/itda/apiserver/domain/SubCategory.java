package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubCategory extends Core {

    private String name;

    @ManyToOne
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;
}
