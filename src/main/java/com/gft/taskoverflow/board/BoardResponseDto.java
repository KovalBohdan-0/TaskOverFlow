package com.gft.taskoverflow.board;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BoardResponseDto(@NotNull Long id, @NotEmpty @Size(max = 100) String title) {
}
