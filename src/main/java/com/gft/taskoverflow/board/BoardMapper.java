package com.gft.taskoverflow.board;

import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public Board mapToEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.title());
        return board;
    }

    public BoardResponseDto mapToResponseDto(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle());
    }
}
