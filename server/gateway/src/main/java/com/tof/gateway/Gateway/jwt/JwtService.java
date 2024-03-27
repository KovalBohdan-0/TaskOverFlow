package com.tof.gateway.Gateway.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String jwt);

    boolean isJwtValid(String jwt, UserDetails userDetails);
}
