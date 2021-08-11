package com.itda.apiserver.jwt;

import com.itda.exception.InvalidTokenException;
import com.itda.exception.TokenNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TokenExtractor {

    private final String JWT_PREFIX = "Bearer";
    private final int JWT_INDEX = 1;

    public String extractToken(String header) {
        verify(header);
        String[] headerValues = header.split(" ");
        return headerValues[JWT_INDEX];
    }

    private void verify(String header) {
        if (header == null || header.isEmpty()) {
            throw new TokenNotFoundException();
        }
        if (!header.startsWith(JWT_PREFIX)) {
            throw new InvalidTokenException();
        }
    }
}
