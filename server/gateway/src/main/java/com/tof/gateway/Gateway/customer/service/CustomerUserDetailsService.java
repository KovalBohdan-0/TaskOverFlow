package com.tof.gateway.Gateway.customer.service;

import com.tof.gateway.Gateway.customer.dto.CustomerUserDetailsDto;
import com.tof.gateway.Gateway.customer.entity.CustomerUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private final AmqpTemplate amqpTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerUserDetailsDto customer = amqpTemplate.convertSendAndReceiveAsType("customer-exchange",
                "customer", username, new ParameterizedTypeReference<>() {});

        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomerUserDetails(customer.email(), customer.password());
    }
}