package com.gft.taskoverflow.board;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BoardMapper {
    BoardResponseDto mapToResponseDto(Board board);
}
