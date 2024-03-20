package com.tof.user.User.customer.service;

import com.tof.user.User.customer.dto.AuthenticationResponse;
import com.tof.user.User.customer.dto.RegistrationDto;
import reactor.core.publisher.Mono;

public interface RegistrationService {
    Mono<AuthenticationResponse> register(RegistrationDto registrationDto);
}
