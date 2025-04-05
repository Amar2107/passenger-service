package com.pac.ride.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${secret.key}")
    private String SECRET_KEY;

    private SecretKey key;

    @PostConstruct
    private void init(){
        key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    long timeout = 3600000;

    public String generateToken(Long id){
        return Jwts.builder()
                .claim("id",id)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+timeout))
                .signWith(key)
                .compact();
    }

    public Long extractId(String token){
        Claims claims =  Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return claims.get("id", Long.class);
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
