package com.gft.taskoverflow.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByTaskId(Long taskId);
    void deleteByTaskId(Long taskId);
}
