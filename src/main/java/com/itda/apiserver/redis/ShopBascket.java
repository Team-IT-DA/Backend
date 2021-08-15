package com.itda.apiserver.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("bascket")
@RequiredArgsConstructor
public class ShopBascket implements Serializable {

    @Id
    private final String authToken;

    private Long productCount = 0L;

    public void addButtonClick() {
        productCount++;
    }

    public void minusButtonClick() {
        if(productCount > 0L) {
            productCount--;
        }
    }

}
