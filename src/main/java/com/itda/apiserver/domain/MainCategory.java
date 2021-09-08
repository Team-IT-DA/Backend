package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MainCategory extends Core {

    private String name;

    @OneToMany(mappedBy = "mainCategory")
    private final List<Product> products = new ArrayList<>();

    public MainCategory(String name) {
        this.name = name;
    }

}
