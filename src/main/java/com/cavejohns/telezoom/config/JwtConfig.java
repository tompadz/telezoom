package com.cavejohns.telezoom.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtConfig {

    private final String apiKey;
    private final Key key;

    public JwtConfig(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.key = Keys.hmacShaKeyFor(apiSecret.getBytes());
    }

    public String generateJwtToken() {
        long expirationTimeMillis = 3600000;
        JwtBuilder builder = Jwts.builder()
                .claim("iss", apiKey)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis));

        return builder.compact();
    }
}
