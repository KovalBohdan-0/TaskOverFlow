package com.gft.taskoverflow.health.check;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-check")
public class HealthCheckController {
    @GetMapping
    public String healthCheck() {
        return "UP";
    }
}
