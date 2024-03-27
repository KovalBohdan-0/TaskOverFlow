package com.tof.user.customer.service.impl;

import com.tof.user.customer.dto.AuthenticationResponse;
import com.tof.user.customer.dto.LoginDto;
import com.tof.user.customer.repository.CustomerRepository;
import com.tof.user.customer.service.LoginService;
import com.tof.user.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.tof.user.exception.exceptions.LoginException;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public Mono<AuthenticationResponse> login(LoginDto loginDto) {
        return customerRepository.findByEmail(loginDto.email())
                .flatMap(customer -> {
                    if (bcryptPasswordEncoder.matches(loginDto.password(), customer.getPassword())) {
                        return Mono.just(customer);
                    } else {
                        return Mono.error(new LoginException("Invalid credentials"));
                    }
                })
                .switchIfEmpty(Mono.error(new LoginException("Invalid credentials")))
                .map(customer -> new AuthenticationResponse(jwtService
                        .generateJwt(new HashMap<>(), loginDto.email())));
    }
}
