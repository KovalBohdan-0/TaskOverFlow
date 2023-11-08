package com.gft.taskoverflow.email;

import java.time.LocalDateTime;

public interface EmailSender {
    void send (String to, String email, String subject);
    String buildEmail(String name, String link);
    String buildNotificationEmail(String message, LocalDateTime notificationTime, String taskName);
}
