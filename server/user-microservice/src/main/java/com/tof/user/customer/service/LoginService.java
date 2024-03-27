package com.tof.user.customer.service;

import com.tof.user.customer.dto.AuthenticationResponse;
import com.tof.user.customer.dto.LoginDto;
import reactor.core.publisher.Mono;

public interface LoginService {
    Mono<AuthenticationResponse> login(LoginDto loginDto);
}
