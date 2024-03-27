package com.tof.gateway.Gateway.jwt;

import com.tof.gateway.Gateway.customer.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtFilter implements ServerSecurityContextRepository {
    private final JwtService jwtService;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return new WebSessionServerSecurityContextRepository().save(exchange, context);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String email = jwtService.extractUsername(jwt);

            if (email != null) {
                UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

                if (jwtService.isJwtValid(jwt, userDetails)) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("X-User", email)
                            .build();
                    return Mono.just(new SecurityContextImpl(auth)).map(securityContext -> {
                        exchange.getAttributes().put("mutatedRequest", mutatedRequest);
                        return securityContext;
                    });
                }
            }
        }

        return Mono.empty();
    }
}