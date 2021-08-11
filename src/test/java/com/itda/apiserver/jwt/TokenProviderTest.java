package com.itda.apiserver.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    private Long userId;

    @Value("${jwt.secret}")
    private String secretKey;

    @BeforeEach
    void setUp() {
        userId = 1L;
    }

    /**
     * 생성한 토큰의 유효기간이 현재 시점 이후임을 보장한다.
     */
    @Test
    @DisplayName("토큰 생성 기능 테스트")
    void createToken() {
        String token = tokenProvider.createToken(userId);
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);

        assertThat(claims.getBody().getExpiration()).isAfter(new Date());

    }

}
