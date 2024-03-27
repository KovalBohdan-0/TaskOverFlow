package com.tof.gateway.Gateway.config;

import com.tof.gateway.Gateway.customer.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(customerUserDetailsService);
        return provider;
    }
}
