package com.gft.taskoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskOverFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskOverFlowApplication.class, args);
    }
}
