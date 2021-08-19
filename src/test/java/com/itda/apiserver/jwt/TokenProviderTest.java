package com.itda.apiserver.jwt;

import com.itda.apiserver.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    private Long userId;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private long validityInMilliseconds;

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

    @Test
    @DisplayName("토큰에서 user id 얻어오는 기능 테스트")
    void getUserId() {
        String token = tokenProvider.createToken(userId);
        Long userIdFromToken = tokenProvider.getUserId(token);

        assertThat(userIdFromToken).isEqualTo(userId);
    }

    @Test
    @DisplayName("유효하지 않은 토큰일 경우, InvalidTokenException 발생")
    void getUserId_invalidToken() {
        String token = createInvalidToken(userId);

        assertThatThrownBy(() -> tokenProvider.getUserId(token)).isInstanceOf(InvalidTokenException.class);
    }

    private String createInvalidToken(Long subject) {
        Claims claims = Jwts.claims().setSubject(subject.toString());

        long aDayAgo = System.currentTimeMillis() - (86400 * 1000);

        Date now = new Date(aDayAgo);
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
