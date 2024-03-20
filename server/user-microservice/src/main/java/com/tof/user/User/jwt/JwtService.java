package com.tof.user.User.jwt;

import java.util.Map;

public interface JwtService {

    String generateJwt(Map<String, Object> extraClaims, String email);
}