package com.gft.taskoverflow.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CustomerDto(@NotEmpty @Size(max = 100) String email, boolean isActivated) { }
