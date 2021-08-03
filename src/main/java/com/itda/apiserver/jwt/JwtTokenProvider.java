package com.itda.apiserver.jwt;

import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;

public class JwtTokenProvider {

    private final String secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret") String secretKey, @Value("${jwt.token-validity-in-seconds") long validityInSeconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInSeconds * 1000;
    }


}
