package com.gft.taskoverflow.task.list.dto;

import com.gft.taskoverflow.task.list.SortDirection;
import com.gft.taskoverflow.task.list.SortOption;
import jakarta.validation.constraints.NotNull;

public record TaskListUpdateSortDto (@NotNull Long id, @NotNull SortOption sortOption, @NotNull SortDirection sortDirection) {
}
