package com.tof.user.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegistrationDto(
        @NotNull(message = "The email can't be null")
        @NotBlank(message = "The email can't be empty")
        @Pattern(regexp = "^([^ ]+@[^ ]+\\.[a-z]{2,6}|)$", message = "Invalid email")
        @Schema(example = "email@gmail.com", description = "User email")
        String email,
        @NotNull(message = "The password can't be null")
        @NotBlank(message = "The password can't be empty")
        @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40})",
                message = "Password must contain at least 8 characters, one uppercase letter, " +
                        "one lowercase letter and one number.")
        @Schema(example = "Password1234", description = "User password")
        String password) {
}
