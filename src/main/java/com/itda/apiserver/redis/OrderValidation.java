package com.itda.apiserver.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value = "order", timeToLive = 1)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderValidation implements Serializable {

    @Id
    private Long userId;

}
