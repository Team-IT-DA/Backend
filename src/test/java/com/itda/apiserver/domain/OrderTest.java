package com.itda.apiserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    @DisplayName("제품 별 주문 하나당 금액은 (제품 가격)*(제품 개수)+(배송비) 이다")
    void pricePerOrder() {

        int productPrice = 10000;
        int deliveryFee = 3000;
        int quantity = 5;

        Product product = Product.builder()
                .price(productPrice)
                .deliveryFee(deliveryFee)
                .build();

        Order order = new Order(quantity, product);

        assertThat(order.getPricePerOrder()).isEqualTo(productPrice * quantity + deliveryFee);
    }
}
