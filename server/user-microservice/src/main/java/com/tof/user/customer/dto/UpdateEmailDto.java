package com.tof.user.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateEmailDto(@NotNull(message = "The email can't be null")
                             @NotBlank(message = "The email can't be empty")
                             @Pattern(regexp = "^([^ ]+@[^ ]+\\.[a-z]{2,6}|)$", message = "Invalid email")
                             @Schema(example = "email@gmail.com", description = "User email")
                             String email,
                             @NotEmpty
                             String password) {
}
