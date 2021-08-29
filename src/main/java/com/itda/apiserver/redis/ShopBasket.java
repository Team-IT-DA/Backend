package com.itda.apiserver.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RedisHash("bascket")
@NoArgsConstructor
public class ShopBasket implements Serializable {

    @Id
    private Long userId;
    private Map<Long, BascketProduct> products = new HashMap();

    public ShopBasket(Long userId) {
        this.userId = userId;
    }

    public void addProduct(BascketProduct product) {
        products.put(product.getProductId(), product);
    }

    public void dropProduct(Long productId) {
        products.remove(productId);
    }

    public BascketProduct getProduct(Long productId) {
        return products.get(productId);
    }

}
