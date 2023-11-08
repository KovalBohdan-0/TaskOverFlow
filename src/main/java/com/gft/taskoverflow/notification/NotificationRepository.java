package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n INNER JOIN n.task t WHERE n.notificationTime < ?1 AND n.isSent = false AND EXISTS(SELECT 1 FROM t.taskList.board.customers m WHERE m.id = ?2)")
    List<Notification> findAllByNotificationTimeBeforeForCustomer(LocalDateTime notificationTime, Long customerId);

    @Query("SELECT n FROM Notification n WHERE n.notificationTime < ?1 AND n.isSent = false")
    List<Notification> findAllByNotificationTimeBefore(LocalDateTime notificationTime);

    @Query("""
                SELECT DISTINCT c
                FROM Customer c
                JOIN FETCH c.boards b
                JOIN FETCH b.taskLists tl
                JOIN FETCH tl.tasks t
                JOIN t.notification n
                WHERE n.id = :notificationId AND n.isSent = false AND c.emailConfirmed = true AND c.onEmailNotifications = true
            """)
    Set<Customer> findCustomersByNotificationId(@Param("notificationId") Long notificationId);

    Optional<Notification> findByTaskId(Long taskId);

    void deleteByTaskId(Long taskId);
}
