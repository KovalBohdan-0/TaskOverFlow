package com.gft.taskoverflow.customer.token;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1")
public class ConfirmationController {
    private final ConfirmationTokenService confirmationTokenService;

    @Operation(summary = "Confirm token")
    @ApiResponse(responseCode = "200", description = "Token confirmed")
    @ApiResponse(responseCode = "404", description = "Token not found", content = @Content)
    @GetMapping("/confirmation")
    public String confirmToken(@RequestParam String token) {
        return confirmationTokenService.confirmToken(token);
    }

    @Operation(summary = "Send confirmation email")
    @ApiResponse(responseCode = "200", description = "Email sent")
    @ApiResponse(responseCode = "409", description = "Confirmation email already exists", content = @Content)
    @GetMapping("/sendConfirmationEmail")
    public void sendConfirmationEmail(@RequestHeader String host) {
        confirmationTokenService.sendConfirmationEmail(host);
    }
}
