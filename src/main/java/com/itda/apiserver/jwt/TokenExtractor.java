package com.itda.apiserver.jwt;

import com.itda.apiserver.exception.InvalidTokenException;
import com.itda.apiserver.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TokenExtractor {

    private static final String JWT_PREFIX = "Bearer";
    private static final int TOKEN_INDEX = 7;

    public String extractToken(String header) {
        verify(header);
        return header.substring(TOKEN_INDEX);
    }

    private void verify(String header) {
        if (header == null) {
            throw new TokenNotFoundException();
        }
        if (!header.startsWith(JWT_PREFIX)) {
            throw new InvalidTokenException();
        }
    }
}
