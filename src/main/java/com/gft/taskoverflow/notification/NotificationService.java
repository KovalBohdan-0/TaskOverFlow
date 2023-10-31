package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import com.gft.taskoverflow.notification.dto.NotificationUpdateDto;
import com.gft.taskoverflow.task.Task;
import com.gft.taskoverflow.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final TaskService taskService;
    private final NotificationMapper notificationMapper;
    private final CustomerService customerService;

    public NotificationResponseDto getNotification(Long taskId) {
        Notification notification = notificationRepository.findByTaskId(taskId).orElse(new Notification());
        return notificationMapper.mapToNotificationResponseDto(notification);
    }

    public List<NotificationResponseDto> getCurrentNotifications() {
        return notificationMapper.mapToNotificationResponseDtoList(
                notificationRepository.findAllByNotificationTimeBeforeForCustomer(LocalDateTime.now(Clock.systemUTC()),
                        customerService.getCurrentCustomerEntity().getId()));
    }

    public void readCurrentNotifications(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);
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

    @Scheduled(fixedDelay = 120000, initialDelay = 60000)
    public void sendNotification() {
        List<Notification> notificationsBeforeNow = notificationRepository.findAllByNotificationTimeBefore(
                LocalDateTime.now(Clock.systemUTC()).minusMinutes(1));

        for (Notification notification : notificationsBeforeNow) {

        }
    }

    public void deleteNotification(Long taskId) {
        notificationRepository.deleteByTaskId(taskId);
    }
}
