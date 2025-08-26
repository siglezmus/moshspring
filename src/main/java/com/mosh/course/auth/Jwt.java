package com.mosh.course.auth;

import com.mosh.course.users.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public Jwt(Claims claims, SecretKey secretKey) {
        this.secretKey = secretKey;
        this.claims = claims;
    }


    public boolean isExpired(){
        return claims.getExpiration().before(new Date());
    }
    public Long getUserId(){
        return Long.valueOf(claims.getSubject());
    }
    public Role getRole(){
        return Role.valueOf(claims.get("role", String.class));
    }

    @Override
    public String toString(){
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }

}
