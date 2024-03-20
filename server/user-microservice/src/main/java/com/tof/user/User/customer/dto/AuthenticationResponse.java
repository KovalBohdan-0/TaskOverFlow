package com.tof.user.User.customer.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationResponse(@NotEmpty String jwt) {}