package com.tof.user.User.customer.service;

import com.tof.user.User.customer.dto.AuthenticationResponse;
import com.tof.user.User.customer.dto.LoginDto;
import reactor.core.publisher.Mono;

import javax.security.auth.login.LoginException;

public interface LoginService {
    Mono<AuthenticationResponse> login(LoginDto loginDto);
}
