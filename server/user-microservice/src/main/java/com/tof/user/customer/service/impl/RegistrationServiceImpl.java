package com.tof.user.customer.service.impl;

import com.tof.user.customer.dto.AuthenticationResponse;
import com.tof.user.customer.dto.RegistrationDto;
import com.tof.user.customer.mapper.CustomerMapper;
import com.tof.user.customer.repository.CustomerRepository;
import com.tof.user.customer.service.RegistrationService;
import com.tof.user.exception.exceptions.DuplicatedResourceException;
import com.tof.user.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    @Transactional
    public Mono<AuthenticationResponse> register(RegistrationDto registrationDto) {
        return customerRepository.findByEmail(registrationDto.email())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DuplicatedResourceException("Email already taken"));
                    }

                    return Mono.just(RegistrationDto.builder()
                                    .email(registrationDto.email())
                                    .password(bcryptPasswordEncoder.encode(registrationDto.password()))
                                    .build())
                            .map(customerMapper::mapToCustomer)
                            .flatMap(customerRepository::insert)
                            .thenReturn(new AuthenticationResponse(jwtService
                                    .generateJwt(new HashMap<>(), registrationDto.email())));
                });
    }
}
