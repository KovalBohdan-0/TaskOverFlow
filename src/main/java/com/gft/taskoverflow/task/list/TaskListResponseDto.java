package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;

import java.util.List;

public record TaskListResponseDto(Long id, String title, Long boardId, List<TaskPreviewDto> tasks, List<Customer> assignedCustomers) {
}
