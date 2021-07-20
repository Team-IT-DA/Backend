package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
}
