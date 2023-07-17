package com.gft.taskoverflow.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SIGN_SECRET;

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new JwtException("Corrupt JWT :" + jwt);
        }
    }

    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        return extractUsername(jwt).equals(userDetails.getUsername());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGN_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwt(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(
                        System.currentTimeMillis())).signWith(getSignInKey(),
                        SignatureAlgorithm.HS256).compact();
    }

    public String generateJwt(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(
                        System.currentTimeMillis())).signWith(getSignInKey(),
                        SignatureAlgorithm.HS256).compact();
    }
}
