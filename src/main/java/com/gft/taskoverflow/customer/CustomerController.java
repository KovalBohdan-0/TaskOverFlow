package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.customer.dto.CustomerDto;
import com.gft.taskoverflow.customer.dto.UpdateEmailDto;
import com.gft.taskoverflow.customer.dto.UpdateNotificationsDto;
import com.gft.taskoverflow.customer.dto.UpdatePasswordDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        log.info("fetching customer with id {}", id);
        return  customerService.getCustomerById(id);
    }

    @GetMapping("/{email}")
    public Customer getCustomerByEmail(@PathVariable String email){
        log.info("fetching customer with id {}", email);
        return  customerService.getCustomerByEmail(email);
    }

    @GetMapping
    public CustomerDto getCurrentCustomer() {
        return customerService.getCurrentCustomer();
    }

    @PostMapping("/update-notifications")
    public void updateNotifications(@RequestBody UpdateNotificationsDto updateNotificationsDto) {
        customerService.updateNotifications(updateNotificationsDto);
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return customerService.updatePassword(updatePasswordDto);
    }

    @PostMapping("/update-email")
    public String updateEmail(@RequestBody UpdateEmailDto updateEmailDto) {
        return customerService.updateEmail(updateEmailDto);
    }
}

