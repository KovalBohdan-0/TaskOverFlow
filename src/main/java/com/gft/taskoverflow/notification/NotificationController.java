package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import com.gft.taskoverflow.notification.dto.NotificationUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/notification/{taskId}")
    public NotificationResponseDto getNotification(@PathVariable Long taskId) {
        return notificationService.getNotification(taskId);
    }

    @GetMapping("/notification")
    public List<NotificationResponseDto> getNotifications() {
        return notificationService.getCurrentNotifications();
    }

    @PutMapping("/notification/{taskId}")
    public void updateNotification(@PathVariable Long taskId, @RequestBody NotificationUpdateDto notification) {
        notificationService.updateNotification(taskId, notification);
    }

    @PutMapping("/notification/read/{taskId}")
    public void readNotifications(@PathVariable Long taskId) {
        notificationService.readCurrentNotifications(taskId);
    }

    @PutMapping("/notification/read")
    public void readAllNotifications() {
        notificationService.readAllCurrentNotifications();
    }

    @DeleteMapping("/notification/{taskId}")
    public void deleteNotification(@PathVariable Long taskId) {
        notificationService.deleteNotification(taskId);
    }
}
