/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.example.demo.entity.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final String jwtSecret = "ThisIsAMuchStrongerAndLongerSecretKeyForHS512AlgorithmThatIsMoreThan512BitsLong1234567890";
    private final int jwtExpirationMs = 86400000;

    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());


    public String generateJwtToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (JwtException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }
}
