package com.tof.user.User.jwt.imp;

import com.tof.user.User.jwt.JwtService;
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

    @Override
    public String generateJwt(Map<String, Object> extraClaims, String email) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(new Date(
                        System.currentTimeMillis())).signWith(getSignInKey()).compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGN_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}