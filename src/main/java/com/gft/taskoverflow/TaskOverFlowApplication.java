package com.gft.taskoverflow;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Authentication needed to access some endpoints"
)
@EnableScheduling
public class TaskOverFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskOverFlowApplication.class, args);
    }
}
