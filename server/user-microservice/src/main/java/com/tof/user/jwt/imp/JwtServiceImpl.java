package com.tof.user.jwt.imp;

import com.tof.user.jwt.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String SIGN_SECRET;
    @Value("${jwt.expiration}")
    private long expirationTime;

    @Override
    public String generateJwt(Map<String, Object> extraClaims, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGN_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}