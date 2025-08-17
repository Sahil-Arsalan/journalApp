package com.alamara.journalApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {

    private String SECRET_KEY="Esr^#tuk68^telmTR$weHYruCrouER#A";

    public String generateToken(String username) {
        HashMap<String,Object> claims=new HashMap<>();
        return createToken(claims,username);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private String createToken(HashMap<String, Object> claims, String subject) {
        return Jwts.builder().claims(claims).subject(subject).header().empty().add("typ","JWT").
                and().issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis()+1000*60*60)).
                signWith(getSigningKey()).compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }


    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
