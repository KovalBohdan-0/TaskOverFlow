package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import com.gft.taskoverflow.notification.dto.NotificationUpdateDto;
import com.gft.taskoverflow.task.Task;
import com.gft.taskoverflow.task.TaskService;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@Data
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final TaskService taskService;
    private final NotificationMapper notificationMapper;
    private final RabbitTemplate rabbitTemplate;

    public NotificationResponseDto getNotification(Long taskId) {
        Notification notification = notificationRepository.findByTaskId(taskId).orElse(new Notification());
        return notificationMapper.mapToNotificationResponseDto(notification);
    }

    public void updateNotification(Long taskId, NotificationUpdateDto notificationUpdateDto) {
        Task task = taskService.getTaskById(taskId);
        Notification notification = notificationRepository.findByTaskId(taskId).orElse(new Notification());
        notification.setMessage(notificationUpdateDto.message());
        notification.setNotificationTime(notificationUpdateDto.notificationTime());
        System.out.println(notificationUpdateDto.notificationTime());
        System.out.println(LocalDateTime.now(Clock.systemUTC()));
        task.setNotification(notification);
        notificationRepository.save(notification);
        taskService.save(task);
    }

    @Scheduled(fixedDelay = 120000, initialDelay = 60000)
    public void sendNotification() {
//        Notification notification = notificationRepository.findFirstByNotificationTimeBefore(LocalDateTime.now(Clock.systemDefaultZone()));
//        if (notification != null) {
            rabbitTemplate.convertAndSend("notification-exchange", "message.type.notification", "Notification sent");
//            notificationRepository.delete(notification);
//        }
    }

    public void deleteNotification(Long taskId) {
        notificationRepository.deleteByTaskId(taskId);
    }
}
