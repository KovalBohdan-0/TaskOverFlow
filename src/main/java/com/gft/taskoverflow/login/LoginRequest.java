package com.gft.taskoverflow.login;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotEmpty @Size(max = 100) String email, @NotEmpty @Size(max = 100) String password) {
}
