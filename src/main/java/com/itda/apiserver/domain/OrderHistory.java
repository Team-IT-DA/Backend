package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory extends Core {

    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "order_sheet_id")
    private OrderSheet orderSheet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public OrderHistory(OrderSheet orderSheet, User user) {
        this.orderSheet = orderSheet;
        this.user = user;
    }
}
