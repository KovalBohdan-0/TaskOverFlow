package com.gft.taskoverflow.customer.token;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ConfirmationController {
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/confirmation")
    public String confirmToken(@RequestParam String token) {
        return confirmationTokenService.confirmToken(token);
    }

    @GetMapping("/sendConfirmationEmail")
    public void sendConfirmationEmail(@RequestHeader String host) {
        confirmationTokenService.sendConfirmationEmail(host);
    }
}
