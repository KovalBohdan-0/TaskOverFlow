package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import com.gft.taskoverflow.notification.dto.NotificationUpdateDto;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/notification/{taskId}")
    public NotificationResponseDto getNotification(@PathVariable Long taskId) {
        return notificationService.getNotification(taskId);
    }

    @PostMapping("/notification/{taskId}")
    public void addNotification(@PathVariable Long taskId, @RequestBody Notification notification) {
        notificationService.addNotification(taskId,notification);
    }

    @PutMapping("/notification/{taskId}")
    public void updateNotification(@PathVariable Long taskId, @RequestBody NotificationUpdateDto notification) {
        notificationService.updateNotification(taskId, notification);
    }

    @DeleteMapping("/notification/{taskId}")
    public void deleteNotification(@PathVariable Long taskId) {
        notificationService.deleteNotification(taskId);
    }
}
