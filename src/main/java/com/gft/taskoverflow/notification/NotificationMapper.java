package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "taskId", source = "task.id")
    NotificationResponseDto mapToNotificationResponseDto(Notification notification);
}
