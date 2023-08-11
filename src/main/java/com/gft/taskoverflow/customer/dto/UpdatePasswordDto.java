package com.gft.taskoverflow.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDto(@NotEmpty String oldPassword, @NotEmpty @Size(min = 4, max = 60) String newPassword) {
}
