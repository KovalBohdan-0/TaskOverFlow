package com.gft.taskoverflow.customer;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomerUserDetails(username, customerService.getCustomerByEmail(username).getPassword());
    }

    public Customer getCustomerByEmail(String email) {
        return customerService.getCustomerByEmail(email);
    }

    public Customer getCurrentCustomer() {
        return customerService.getCustomerByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public String getCurrentCustomerEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
