package com.gft.taskoverflow.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.notificationTime < ?1 AND n.isSent = false")
    List<Notification> findAllByNotificationTimeBefore(LocalDateTime notificationTime);
    Optional<Notification> findByTaskId(Long taskId);
    void deleteByTaskId(Long taskId);
}
