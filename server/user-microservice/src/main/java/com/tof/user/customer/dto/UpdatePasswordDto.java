package com.tof.user.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdatePasswordDto(@NotEmpty String oldPassword,
                                @NotNull(message = "The password can't be null")
                                @NotBlank(message = "The password can't be empty")
                                @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,40})",
                                        message = "Password must contain at least 8 characters, one uppercase " +
                                                "letter, one lowercase letter and one number.")
                                @Schema(example = "Password1234", description = "New user password")
                                String newPassword) {
}
