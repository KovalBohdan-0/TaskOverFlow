package com.gft.taskoverflow.registration;

import com.gft.taskoverflow.login.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public AuthenticationResponse register(@Valid @RequestBody RegistrationRequest request, @RequestHeader String host) {
        return new AuthenticationResponse(registrationService.register(request, host));
    }
}
