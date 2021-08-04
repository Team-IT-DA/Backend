package com.itda.apiserver.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.Date;

public class TokenProvider {

    private final String secretKey;
    private final long validityInMilliseconds;

    public TokenProvider(@Value("${jwt.secret") String secretKey, @Value("${jwt.token-validity-in-seconds") long validityInSeconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInSeconds * 1000;
    }

    public String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
