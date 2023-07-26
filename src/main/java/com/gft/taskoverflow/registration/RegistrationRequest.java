package com.gft.taskoverflow.registration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(@NotEmpty @Size(max = 100) String email, @NotEmpty @Size(max = 100) String password) {
}

