package com.gft.taskoverflow.registration;

import com.gft.taskoverflow.login.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    @Operation(summary = "Register new user")
    @ApiResponse(responseCode = "200", description = "User registered")
    public AuthenticationResponse register(@Valid @RequestBody RegistrationRequest request, @RequestHeader String host) {
        return new AuthenticationResponse(registrationService.register(request, host));
    }
}
