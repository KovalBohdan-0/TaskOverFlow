package com.gft.taskoverflow.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CustomerDto(@NotEmpty @Size(max = 100) String email, boolean emailConfirmed, boolean onEmailNotifications,
                          boolean onSiteNotifications) {
}
