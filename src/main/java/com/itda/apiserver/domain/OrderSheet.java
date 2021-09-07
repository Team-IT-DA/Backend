package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderSheet extends Core {

    private Integer totalPrice;
    private Boolean paidYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orderSheet", cascade = CascadeType.ALL)
    private final List<Order> orders = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_info_id")
    private ShippingInfo shippingInfo;

    private OrderSheet(Integer totalPrice, User user, ShippingInfo shippingInfo) {
        this.totalPrice = totalPrice;
        this.user = user;
        this.shippingInfo = shippingInfo;
    }

    public static OrderSheet createOrderSheet(Integer totalPrice, User user, ShippingInfo shippingInfo, List<Order> orders) {
        OrderSheet orderSheet = new OrderSheet(totalPrice, user, shippingInfo);
        orderSheet.addOrders(orders);
        orderSheet.paidYn = false;
        return orderSheet;
    }

    private void addOrders(List<Order> orders) {
        this.orders.addAll(orders);
        orders.stream()
                .forEach(order -> order.setOrderSheet(this));
    }

    public boolean isSameUser(Long userId) {
        return this.user.getId().equals(userId);
    }
}
