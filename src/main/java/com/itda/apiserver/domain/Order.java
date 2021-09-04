package com.itda.apiserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Getter
public class Order extends Core {

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_sheet_id")
    private OrderSheet orderSheet;

    public Order(Integer quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public void setOrderSheet(OrderSheet orderSheet) {
        this.orderSheet = orderSheet;
    }

    public int getPricePerOrder() {
        return quantity * product.getPrice() + product.getDeliveryFee();
    }
}
