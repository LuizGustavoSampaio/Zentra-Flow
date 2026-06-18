package com.zentra.zentra_flow.services;

import com.zentra.zentra_flow.entities.Client;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Value("${api.security.token.expiration}")
    private long expirationTime;

    public String generateToken(Client client) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(client.getEmail())
                .claim("id", client.getId())
                .claim("role", client.getClass().getSimpleName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
