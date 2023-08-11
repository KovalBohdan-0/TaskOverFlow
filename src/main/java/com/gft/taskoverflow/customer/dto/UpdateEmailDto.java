package com.gft.taskoverflow.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateEmailDto(@NotEmpty @Size(max = 100) String email, @NotEmpty String password) {
}
