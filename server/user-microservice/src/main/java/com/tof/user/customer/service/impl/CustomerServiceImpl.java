package com.tof.user.customer.service.impl;

import com.tof.user.customer.dto.*;
import com.tof.user.customer.entity.Customer;
import com.tof.user.customer.mapper.CustomerMapper;
import com.tof.user.customer.repository.CustomerRepository;
import com.tof.user.customer.service.CustomerService;
import com.tof.user.exception.exceptions.DuplicatedResourceException;
import com.tof.user.exception.exceptions.LoginException;
import com.tof.user.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Mono<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Mono<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Mono<CustomerDto> getCurrentCustomer(ServerWebExchange exchange) {
        String id = exchange.getRequest().getHeaders().getFirst("X-User");

        if (id != null) {
            return customerRepository.findById(id).map(customerMapper::mapToCustomerDto);
        } else {
            return Mono.error(new RuntimeException("X-User header not present"));
        }
    }

    @Override
    public Mono<Customer> getCurrentCustomerEntity(ServerWebExchange exchange) {
        String id = exchange.getRequest().getHeaders().getFirst("X-User");

        if (id != null) {
            return customerRepository.findById(id);
        } else {
            return Mono.error(new RuntimeException("X-User header not present"));
        }
    }

    @Override
    public Mono<String> getCurrentCustomerId(ServerWebExchange exchange) {
        String id = exchange.getRequest().getHeaders().getFirst("X-User");

        if (id != null) {
            return Mono.just(id);
        } else {
            return Mono.error(new RuntimeException("X-User header not present"));
        }
    }

    @Override
    public Mono<Void> confirmEmail(String email) {
        Mono<Customer> customerToConfirm = getCustomerByEmail(email);
        return customerToConfirm
                .flatMap(customer -> {
                    customer.setEmailConfirmed(true);
                    return customerRepository.save(customer);
                })
                .then();
    }

    @Override
    public Mono<AuthenticationResponse> updatePassword(ServerWebExchange exchange, UpdatePasswordDto updatePasswordDto) {
        return getCurrentCustomerEntity(exchange)
                .flatMap(customer -> {
                    if (bCryptPasswordEncoder.matches(updatePasswordDto.oldPassword(), customer.getPassword())) {
                        customer.setPassword(bCryptPasswordEncoder.encode(updatePasswordDto.newPassword()));
                        return customerRepository.save(customer);
                    } else {
                        return Mono.error(new LoginException("Password is incorrect"));
                    }
                })
                .map(savedCustomer -> new AuthenticationResponse(jwtService
                        .generateJwt(new HashMap<>(), savedCustomer.getEmail())));
    }

    @Override
    public Mono<AuthenticationResponse> updateEmail(ServerWebExchange exchange, UpdateEmailDto updateEmailDto) {
        return getCurrentCustomerEntity(exchange)
                .flatMap(customer -> {
                    if (bCryptPasswordEncoder.matches(updateEmailDto.password(), customer.getPassword())) {
                        return existsByEmail(updateEmailDto.email())
                                .flatMap(exists -> {
                                    if (!exists) {
                                        customer.setEmail(updateEmailDto.email());
                                        return customerRepository.save(customer);
                                    } else {
                                        return Mono.error(new DuplicatedResourceException("Email already exists"));
                                    }
                                });
                    } else {
                        return Mono.error(new LoginException("Password is incorrect"));
                    }
                })
                .map(savedCustomer -> new AuthenticationResponse(jwtService
                        .generateJwt(new HashMap<>(), savedCustomer.getEmail())));
    }

    @Override
    public Mono<Void> save(Customer customer) {
        return customerRepository.save(customer).then();
    }

    @RabbitListener(queues = "customer-queue")
    public CustomerUserDetailsDto getCustomer(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::mapToCustomerUserDetailsDto)
                .block();
    }
}
