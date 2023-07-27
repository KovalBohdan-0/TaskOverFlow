package com.gft.taskoverflow.task.list.dto;

import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TaskListResponseDto(@NotNull Long id, @NotEmpty @Size(max = 100) String title, @NotNull Float position , @NotNull Long boardId, @NotNull List<TaskPreviewDto> tasks, @NotNull List<Customer> assignedCustomers) {
}
