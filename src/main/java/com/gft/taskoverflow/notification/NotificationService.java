package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import com.gft.taskoverflow.notification.dto.NotificationUpdateDto;
import com.gft.taskoverflow.task.Task;
import com.gft.taskoverflow.task.TaskService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final TaskService taskService;
    private final NotificationMapper notificationMapper;

    public NotificationResponseDto getNotification(Long taskId) {
        Notification notification = notificationRepository.findByTaskId(taskId).orElse(new Notification());
        return notificationMapper.mapToNotificationResponseDto(notification);
    }
    public void updateNotification(Long taskId, NotificationUpdateDto notificationUpdateDto) {
        Task task = taskService.getTaskById(taskId);
        Notification notification = notificationRepository.findByTaskId(taskId).orElse(new Notification());
        notification.setMessage(notificationUpdateDto.message());
        notification.setNotificationTime(notificationUpdateDto.notificationTime());
        task.setNotification(notification);
        notificationRepository.save(notification);
        taskService.save(task);
    }

    public void deleteNotification(Long taskId) {
        notificationRepository.deleteByTaskId(taskId);
    }
}
