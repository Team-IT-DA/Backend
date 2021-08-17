package com.itda.apiserver.jwt;

import com.itda.apiserver.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        assertThat(extractedToken).isEqualTo(token);
    }

    @Test
    @DisplayName("토큰 추출 시 유효하지 않은 토큰일 경우 InvalidTokenException 발생")
    void extractInvalidToken() {
        String invalidValue = "thisIsInvalidToken";

        assertThatThrownBy(() -> tokenExtractor.extractToken(invalidValue))
                .isInstanceOf(InvalidTokenException.class);
    }
}
