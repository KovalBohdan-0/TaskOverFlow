package com.tof.user.customer.service;

import com.tof.user.customer.dto.AuthenticationResponse;
import com.tof.user.customer.dto.RegistrationDto;
import reactor.core.publisher.Mono;

public interface RegistrationService {
    Mono<AuthenticationResponse> register(RegistrationDto registrationDto);
}
