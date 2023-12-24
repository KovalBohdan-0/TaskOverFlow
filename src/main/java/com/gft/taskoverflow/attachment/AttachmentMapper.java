package com.gft.taskoverflow.attachment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    @Mapping(target = "taskId", source = "task.id")
    AttachmentResponseDto mapToResponseDto(Attachment attachment);
}
