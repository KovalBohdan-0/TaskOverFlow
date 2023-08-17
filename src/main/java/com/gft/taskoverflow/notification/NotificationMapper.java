package com.gft.taskoverflow.notification;

import com.gft.taskoverflow.notification.dto.NotificationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "isRead", source = "read")
    @Mapping(target = "taskId", source = "task.id")
    NotificationResponseDto mapToNotificationResponseDto(Notification notification);
    List<NotificationResponseDto> mapToNotificationResponseDtoList(List<Notification> notifications);
}
