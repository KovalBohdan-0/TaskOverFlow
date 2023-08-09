package com.gft.taskoverflow.notification.dto;

import java.time.LocalDateTime;

public record NotificationUpdateDto(String message, LocalDateTime notificationTime) {
}
