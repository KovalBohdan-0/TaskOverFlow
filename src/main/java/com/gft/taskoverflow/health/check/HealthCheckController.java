package com.gft.taskoverflow.health.check;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-check")
public class HealthCheckController {

    @Operation(summary = "Health check")
    @ApiResponse(responseCode = "200", description = "Health check")
    @GetMapping
    public String healthCheck() {
        return "UP";
    }
}
