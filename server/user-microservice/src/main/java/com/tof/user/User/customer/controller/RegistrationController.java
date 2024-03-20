package com.tof.user.User.customer.controller;

import com.tof.user.User.customer.dto.AuthenticationResponse;
import com.tof.user.User.customer.dto.RegistrationDto;
import com.tof.user.User.customer.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    @Operation(summary = "Register new user")
    @ApiResponse(responseCode = "200", description = "User registered")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "409", description = "User already exists")
    public Mono<AuthenticationResponse> register(@Valid @RequestBody RegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }
}
