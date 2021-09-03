package com.itda.apiserver.redis;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("product")
@AllArgsConstructor
@NoArgsConstructor
public class BasketProduct implements Serializable {
    private Long productId;
    private String productName;
    private int price;
    private int shippingFee;
    private String imgUrl;
    private int productCount = 0;
    private String sellerName;

    public void addButtonClick() {
        productCount++;
    }

    public void minusButtonClick() {
        if(productCount > 0L) {
            productCount--;
        }
    }

    public int getProductCount() {
        return productCount;
    }
}
