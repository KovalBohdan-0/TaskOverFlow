package com.gft.taskoverflow.login;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationResponse(@NotEmpty String jwt) {}
