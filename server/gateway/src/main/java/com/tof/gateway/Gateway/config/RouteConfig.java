package com.tof.gateway.Gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/customer/**", "/api/v1/registration", "/api/v1/login")
                        .uri("http://localhost:8081"))
                .route(p -> p
                        .path("/board/**")
                        .uri("lb://board"))
                .build();
    }
}
