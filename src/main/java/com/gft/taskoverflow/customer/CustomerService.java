package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.exception.DuplicateResourceException;
import com.gft.taskoverflow.exception.ResourceNotFoundException;
import com.gft.taskoverflow.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with email \"%s\" not found".formatted(email)
                ));
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with email \"%s\" not found".formatted(id)
                ));
    }

    public void signUpCustomer(RegistrationRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "email \"%s\" has been already taken".formatted(request.email())
            );
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.password());

        Customer customer = Customer.builder()
                .email(request.email())
                .password(encodedPassword)
                .build();

        customerRepository.save(customer);
    }

    public CustomerDto getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = getCustomerByEmail(authentication.getName());
        return new CustomerDto(customer.getEmail(), customer.isEmailConfirmed());
    }

    public Customer getCurrentCustomerEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getCustomerByEmail(authentication.getName());
    }

    public boolean currentCustomerContainsBoard(Long boardId) {
        Customer customer = getCurrentCustomerEntity();
        return customerRepository.containsBoardByIdAndCustomerId(boardId, customer.getId());
    }

    public void confirmEmail(String email) {
        Customer customer = getCustomerByEmail(email);
        customer.setEmailConfirmed(true);
        customerRepository.save(customer);
    }
}
