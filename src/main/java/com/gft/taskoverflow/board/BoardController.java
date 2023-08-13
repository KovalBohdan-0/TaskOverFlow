package com.gft.taskoverflow.board;

import com.gft.taskoverflow.board.dto.BoardAddCustomerDto;
import com.gft.taskoverflow.board.dto.BoardResponseDto;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public Long addBoard(@Valid @RequestBody Board board) {
        return boardService.saveBoard(board);
    }

    @PostMapping("/addCustomer/{boardId}")
    public void addCustomer(@PathVariable Long boardId, @RequestBody BoardAddCustomerDto addCustomerDto) {
        boardService.saveBoardCustomers(boardId, addCustomerDto.email());
    }

    @GetMapping
    public List<BoardResponseDto> getBoards() {
        return boardService.getCustomersBoards();
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
