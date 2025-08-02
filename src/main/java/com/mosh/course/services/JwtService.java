package com.mosh.course.services;

import com.mosh.course.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateAccessToken(User user){
        final long tokenExpiration = 300; // 5m
        return getToken(user, tokenExpiration);
    }

    public String generateRefreshToken(User user){
        final long tokenExpiration = 604800; // 7d
        return getToken(user, tokenExpiration);
    }

    private String getToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token){

        try{
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException ex){
            return false;
        }

    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }

    public String getEmailFromToken(String token){
        return getClaims(token).get("email", String.class);
    }

}
