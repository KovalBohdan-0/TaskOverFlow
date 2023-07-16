package com.example.oauth.config;

import com.example.oauth.customer.CustomerUserDetailsService;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@Data
public class WebConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder.bCryptPasswordEncoder());
        provider.setUserDetailsService(customerUserDetailsService);
        return provider;
    }
}
