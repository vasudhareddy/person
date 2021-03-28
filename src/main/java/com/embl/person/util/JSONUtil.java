package com.embl.person.util;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.function.*;

@Component
public class JSONUtil {
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey("secret1234").parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, "secret1234").compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return userDetails.getUsername().equals(userName) && !isTokenExpired(token);
    }

    private String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
