package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import com.gft.taskoverflow.notification.dto.NotificationUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1")
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "Get notification by task id")
    @ApiResponse(responseCode = "200", description = "Notification")
    @GetMapping("/notification/{taskId}")
    public NotificationResponseDto getNotification(@PathVariable Long taskId) {
        return notificationService.getNotification(taskId);
    }

    @Operation(summary = "Get all notifications")
    @ApiResponse(responseCode = "200", description = "List of notifications")
    @GetMapping("/notification")
    public List<NotificationResponseDto> getNotifications() {
        return notificationService.getCurrentNotifications();
    }

    @Operation(summary = "Update notification")
    @ApiResponse(responseCode = "200", description = "Notification updated")
    @PutMapping("/notification/{taskId}")
    public void updateNotification(@PathVariable Long taskId, @RequestBody NotificationUpdateDto notification) {
        notificationService.updateNotification(taskId, notification);
    }

    @Operation(summary = "Read notification")
    @ApiResponse(responseCode = "200", description = "Notification read")
    @PutMapping("/notification/read/{taskId}")
    public void readNotifications(@PathVariable Long taskId) {
        notificationService.readCurrentNotifications(taskId);
    }

    @Operation(summary = "Read all notifications")
    @ApiResponse(responseCode = "200", description = "All notifications read")
    @PutMapping("/notification/read")
    public void readAllNotifications() {
        notificationService.readAllCurrentNotifications();
    }

    @Operation(summary = "Delete notification")
    @ApiResponse(responseCode = "200", description = "Notification deleted")
    @DeleteMapping("/notification/{taskId}")
    public void deleteNotification(@PathVariable Long taskId) {
        notificationService.deleteNotification(taskId);
    }
}
