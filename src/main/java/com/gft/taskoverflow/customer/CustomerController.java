package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.customer.dto.CustomerDto;
import com.gft.taskoverflow.customer.dto.UpdateEmailDto;
import com.gft.taskoverflow.customer.dto.UpdateNotificationsDto;
import com.gft.taskoverflow.customer.dto.UpdatePasswordDto;
import com.gft.taskoverflow.login.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get customer by id")
    @ApiResponse(responseCode = "200", description = "Customer")
    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        log.info("fetching customer with id {}", id);
        return  customerService.getCustomerById(id);
    }

    @Operation(summary = "Get customer by email")
    @ApiResponse(responseCode = "200", description = "Customer")
    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    @GetMapping("/{email}")
    public Customer getCustomerByEmail(@PathVariable String email){
        log.info("fetching customer with id {}", email);
        return  customerService.getCustomerByEmail(email);
    }

    @Operation(summary = "Get current customer")
    @ApiResponse(responseCode = "200", description = "Customer")
    @GetMapping
    public CustomerDto getCurrentCustomer() {
        return customerService.getCurrentCustomer();
    }

    @Operation(summary = "Update notifications")
    @ApiResponse(responseCode = "200", description = "Notifications updated")
    @PostMapping("/update-notifications")
    public void updateNotifications(@RequestBody UpdateNotificationsDto updateNotificationsDto) {
        customerService.updateNotifications(updateNotificationsDto);
    }

    @Operation(summary = "Update password")
    @ApiResponse(responseCode = "200", description = "Password updated")
    @ApiResponse(responseCode = "403", description = "Incorrect password", content = @Content)
    @PostMapping("/update-password")
    public AuthenticationResponse updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return customerService.updatePassword(updatePasswordDto);
    }

    @Operation(summary = "Update email")
    @ApiResponse(responseCode = "200", description = "Email updated")
    @ApiResponse(responseCode = "403", description = "Incorrect password", content = @Content)
    @ApiResponse(responseCode = "409", description = "Email already exists", content = @Content)
    @PostMapping("/update-email")
    public AuthenticationResponse updateEmail(@RequestBody UpdateEmailDto updateEmailDto) {
        return customerService.updateEmail(updateEmailDto);
    }
}

