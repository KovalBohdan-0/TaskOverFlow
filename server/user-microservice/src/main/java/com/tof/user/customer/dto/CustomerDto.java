package com.tof.user.customer.dto;

public record CustomerDto(String id, String name, String email, String password, boolean emailConfirmed,
                          boolean onEmailNotifications, boolean onSiteNotifications) {
}
