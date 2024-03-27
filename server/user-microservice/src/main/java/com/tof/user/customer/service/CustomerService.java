package com.tof.user.customer.service;

import com.tof.user.customer.dto.AuthenticationResponse;
import com.tof.user.customer.dto.CustomerDto;
import com.tof.user.customer.dto.UpdateEmailDto;
import com.tof.user.customer.dto.UpdatePasswordDto;
import com.tof.user.customer.entity.Customer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Boolean> existsByEmail(String email);

    Mono<Customer> getCustomerByEmail(String email);

    Mono<Customer> getCustomerById(String id);

    Mono<CustomerDto> getCurrentCustomer(ServerWebExchange exchange);

    Mono<Customer> getCurrentCustomerEntity(ServerWebExchange exchange);

    Mono<String> getCurrentCustomerId(ServerWebExchange exchange);

    Mono<Void> confirmEmail(String email);

    Mono<AuthenticationResponse> updatePassword(ServerWebExchange exchange, UpdatePasswordDto updatePasswordDto);

    Mono<AuthenticationResponse> updateEmail(ServerWebExchange exchange, UpdateEmailDto updateEmailDto);

    Mono<Void> save(Customer customer);
}
