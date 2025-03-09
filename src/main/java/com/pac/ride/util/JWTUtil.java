package com.pac.ride.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private static final String SECRET_KEY = "mySuperSecretKey12345678901234567890123456789012";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    long timeout = 3600000;

    public String generateToken(Long passengerId){
        return Jwts.builder()
                .claim("passengerId",passengerId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+timeout))
                .signWith(key)
                .compact();
    }

    public Long extractPassengerId(String token){
        Claims claims =  Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return claims.get("passengerId", Long.class);
    }

    public boolean validateToken(String token){
        try {
                Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
                return true;
        }catch (Exception e){
            return false;
        }
    }

}
