package com.gft.taskoverflow.board;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public void addBoard(@RequestBody BoardDto boardDto) {
        boardService.saveBoard(boardDto);
    }

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestParam Long boardId, @RequestParam String email) {
        boardService.saveBoardCustomers(boardId, email);
    }

    @GetMapping
    public List<BoardResponseDto> getBoards() {
        return boardService.getCustomersBoards();
    }
}
