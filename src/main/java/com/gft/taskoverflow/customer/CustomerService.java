package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.customer.dto.CustomerDto;
import com.gft.taskoverflow.customer.dto.UpdateEmailDto;
import com.gft.taskoverflow.customer.dto.UpdateNotificationsDto;
import com.gft.taskoverflow.customer.dto.UpdatePasswordDto;
import com.gft.taskoverflow.exception.DuplicateResourceException;
import com.gft.taskoverflow.exception.IncorrectPasswordException;
import com.gft.taskoverflow.exception.ResourceNotFoundException;
import com.gft.taskoverflow.jwt.JwtService;
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
    private final JwtService jwtService;

    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("customer with email \"%s\" not found".formatted(email)));
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("customer with email \"%s\" not found".formatted(id)));
    }

    public void signUpCustomer(RegistrationRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("email \"%s\" has been already taken".formatted(request.email()));
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.password());

        Customer customer = Customer.builder().email(request.email()).password(encodedPassword).build();

        customerRepository.save(customer);
    }

    public CustomerDto getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = getCustomerByEmail(authentication.getName());
        return new CustomerDto(customer.getEmail(), customer.isEmailConfirmed(), customer.isOnEmailNotifications(), customer.isOnSiteNotifications());
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

    public void updateNotifications(UpdateNotificationsDto updateNotificationsDto) {
        Customer customer = getCurrentCustomerEntity();
        customer.setOnEmailNotifications(updateNotificationsDto.onEmailNotifications());
        customer.setOnSiteNotifications(updateNotificationsDto.onSiteNotifications());
        customerRepository.save(customer);
    }

    public String updatePassword(UpdatePasswordDto updatePasswordDto) {
        Customer customer = getCurrentCustomerEntity();

        if (!bCryptPasswordEncoder.encode(updatePasswordDto.oldPassword()).equals(customer.getPassword())) {
            throw new IncorrectPasswordException("old password is incorrect");
        }

        customer.setPassword(bCryptPasswordEncoder.encode(updatePasswordDto.newPassword()));
        customerRepository.save(customer);
        return jwtService.generateJwt(new CustomerUserDetails(customer.getEmail(), updatePasswordDto.newPassword()));
    }

    public String updateEmail(UpdateEmailDto updateEmailDto) {
        Customer customer = getCurrentCustomerEntity();

//        System.out.println(updateEmailDto.password());
//        System.out.println(bCryptPasswordEncoder.encode(updateEmailDto.password()));
//        System.out.println(customer.getPassword());
        if (!bCryptPasswordEncoder.encode(updateEmailDto.password()).equals(customer.getPassword())) {
            throw new IncorrectPasswordException("password is incorrect");
        } else if (customerRepository.existsByEmail(updateEmailDto.email())) {
            throw new DuplicateResourceException("email \"%s\" has been already taken".formatted(updateEmailDto.email()));
        }

        customer.setEmail(updateEmailDto.email());
        customerRepository.save(customer);
        return jwtService.generateJwt(new CustomerUserDetails(updateEmailDto.email(), updateEmailDto.password()));
    }
}
