package com.tof.gateway.Gateway.jwt.impl;

import com.tof.gateway.Gateway.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String SIGN_SECRET;

    @Override
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    @Override
    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        return extractUsername(jwt).equals(userDetails.getUsername()) && !isJwtExpired(jwt);
    }

    private Claims extractAllClaims(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build().parseSignedClaims(jwt).getPayload();
        } catch (Exception e) {
            throw new JwtException("Corrupt JWT :" + jwt);
        }
    }

    private boolean isJwtExpired(String jwt) {
        return extractAllClaims(jwt).getExpiration().before(new Date());
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGN_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}