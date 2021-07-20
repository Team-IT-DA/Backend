package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSheet extends Core {

    private Integer totalPrice;
    private Boolean paidYn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "orderSheet")
    private OrderHistory orderHistory;

    @OneToMany(mappedBy = "orderSheet")
    private final List<Order> orders = new ArrayList<>();
}
