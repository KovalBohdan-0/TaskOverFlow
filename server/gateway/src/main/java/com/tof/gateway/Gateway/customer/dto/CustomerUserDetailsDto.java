package com.tof.gateway.Gateway.customer.dto;

import java.io.Serializable;

public record CustomerUserDetailsDto(String email, String password) implements Serializable {
}
