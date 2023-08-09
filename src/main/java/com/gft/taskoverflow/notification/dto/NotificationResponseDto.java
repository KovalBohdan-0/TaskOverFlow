package com.gft.taskoverflow.notification.dto;

import java.time.LocalDateTime;

public record NotificationResponseDto (Long id, String message, LocalDateTime notificationTime, Long taskId) {
}
