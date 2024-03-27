package com.tof.gateway.Gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Lazy
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final UserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return Mono.just(authentication)
                .filter(auth -> auth.getCredentials() instanceof String)
                .cast(UsernamePasswordAuthenticationToken.class)
                .flatMap(auth -> Mono.just(auth.getCredentials().toString()))
                .filter(password -> userDetails.getPassword().equals(password))
                .map(password -> new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities()));
    }
}
