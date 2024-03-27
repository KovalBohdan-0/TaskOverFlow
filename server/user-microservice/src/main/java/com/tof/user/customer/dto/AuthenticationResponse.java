package com.tof.user.customer.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationResponse(@NotEmpty String jwt) {}
