package com.tof.gateway.Gateway.config;

import com.tof.user.User.microservice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Lazy
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final CustomerService customerService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String email = authentication.getName();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, authentication.getCredentials());
        //TODO add more user validation logic here.
        return Mono.just(authentication);
    }
}
