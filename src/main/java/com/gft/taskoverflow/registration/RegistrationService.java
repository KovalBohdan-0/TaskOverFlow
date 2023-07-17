package com.gft.taskoverflow.registration;

import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.customer.CustomerUserDetails;
import com.gft.taskoverflow.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final CustomerService customerService;
    private final JwtService jwtService;

    @Transactional
    public String register(RegistrationRequest request, String host) {
        customerService.signUpCustomer(request);
        UserDetails userDetails = new CustomerUserDetails(request.email(), request.password());
        return jwtService.generateJwt(new HashMap<>(), userDetails);
    }
}
