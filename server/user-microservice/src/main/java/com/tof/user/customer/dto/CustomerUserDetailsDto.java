package com.tof.user.customer.dto;

import java.io.Serializable;

public record CustomerUserDetailsDto(String email, String password) implements Serializable {
}
