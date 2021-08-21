package com.itda.apiserver.redis;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("product")
@AllArgsConstructor
@NoArgsConstructor
public class BascketProduct implements Serializable {
    private Long productId;
    private String productName;
    private int price;
    private String imgUrl;
    private int productCount = 0;

    public BascketProduct(Long productId, String productName, String imgUrl, int price) {
        this.productId = productId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.price = price;
    }

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
