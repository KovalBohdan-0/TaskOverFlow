package com.tof.user.customer.controller;

import com.tof.user.customer.dto.AuthenticationResponse;
import com.tof.user.customer.dto.CustomerDto;
import com.tof.user.customer.dto.UpdateEmailDto;
import com.tof.user.customer.dto.UpdatePasswordDto;
import com.tof.user.customer.entity.Customer;
import com.tof.user.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "Get customer by id")
    @ApiResponse(responseCode = "200", description = "Customer")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{id}")
    public Mono<Customer> getCustomerById(@PathVariable String id) {
        log.info("fetching customer with id {}", id);
        return customerService.getCustomerById(id);
    }

    @Operation(summary = "Get customer by email")
    @ApiResponse(responseCode = "200", description = "Customer")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("email/{email}")
    public Mono<Customer> getCustomerByEmail(@PathVariable String email) {
        log.info("fetching customer with id {}", email);
        return customerService.getCustomerByEmail(email);
    }

    @Operation(summary = "Get current customer")
    @ApiResponse(responseCode = "200", description = "Customer")
    @GetMapping
    public Mono<CustomerDto> getCurrentCustomer(ServerWebExchange serverWebExchange) {
        return customerService.getCurrentCustomer(serverWebExchange);
    }

    @Operation(summary = "Update password")
    @ApiResponse(responseCode = "200", description = "Password updated")
    @ApiResponse(responseCode = "401", description = "Incorrect password")
    @PostMapping("/update-password")
    public Mono<AuthenticationResponse> updatePassword(ServerWebExchange serverWebExchange,
                                                       @RequestBody UpdatePasswordDto updatePasswordDto) {
        return customerService.updatePassword(serverWebExchange, updatePasswordDto);
    }

    @Operation(summary = "Update email")
    @ApiResponse(responseCode = "200", description = "Email updated")
    @ApiResponse(responseCode = "401", description = "Incorrect password")
    @ApiResponse(responseCode = "409", description = "Email already exists")
    @PostMapping("/update-email")
    public Mono<AuthenticationResponse> updateEmail(ServerWebExchange serverWebExchange,
                                                    @RequestBody UpdateEmailDto updateEmailDto) {
        return customerService.updateEmail(serverWebExchange, updateEmailDto);
    }
}
