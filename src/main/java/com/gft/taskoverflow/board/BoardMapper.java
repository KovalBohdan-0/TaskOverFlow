package com.gft.taskoverflow.board;

import com.gft.taskoverflow.board.dto.BoardResponseDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BoardMapper {
    BoardResponseDto mapToResponseDto(Board board);
}
