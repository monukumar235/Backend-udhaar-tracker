package com.udhaartracker.security;

import com.udhaartracker.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value(("${jwt.expiration}"))
    private long expiration;


    private SecretKey getSecretKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("tokenVersion",user.getTokenVersion())
                .expiration(new Date(now.getTime()+expiration))
                .signWith(getSecretKey())
                .issuedAt(now)
                .compact();
    }

    public String extractEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Integer extractTokenVersionFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("tokenVersion", Integer.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith( getSecretKey() )
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (JwtException |  IllegalArgumentException e) {
            return false;
        }
    }


}
