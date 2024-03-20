package com.tof.user.User.customer.controller;

import com.tof.user.User.customer.dto.AuthenticationResponse;
import com.tof.user.User.customer.dto.LoginDto;
import com.tof.user.User.customer.dto.RegistrationDto;
import com.tof.user.User.customer.service.LoginService;
import com.tof.user.User.customer.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "200", description = "User logged in successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    public Mono<AuthenticationResponse> register(@Valid @RequestBody LoginDto loginDto) {
        return loginService.login(loginDto);
    }
}
