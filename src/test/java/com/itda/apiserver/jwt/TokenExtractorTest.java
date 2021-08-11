package com.itda.apiserver.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenExtractorTest {

    @Autowired
    private TokenExtractor tokenExtractor;

    private String token = "thisIsToken";

    @Test
    @DisplayName("헤더에서 토큰 추출하는 기능 테스트")
    void extractToken() {
        String value = "Bearer thisIsToken";
        String extractedToken = tokenExtractor.extractToken(value);

        Assertions.assertThat(extractedToken).isEqualTo(token);
    }
}
