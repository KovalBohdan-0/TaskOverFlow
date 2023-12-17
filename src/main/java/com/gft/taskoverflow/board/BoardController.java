package com.gft.taskoverflow.board;

import com.gft.taskoverflow.board.dto.BoardAddCustomerDto;
import com.gft.taskoverflow.board.dto.BoardResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/board")
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "Add new board")
    @ApiResponse(responseCode = "200", description = "Board added")
    @PostMapping
    public Long addBoard(@Valid @RequestBody Board board) {
        return boardService.saveBoard(board);
    }

    @Operation(summary = "Add customer to board")
    @ApiResponse(responseCode = "200", description = "Customer added")
    @ApiResponse(responseCode = "404", description = "Board not found", content = @Content)
    @PostMapping("/addCustomer/{boardId}")
    public void addCustomer(@PathVariable Long boardId, @RequestBody BoardAddCustomerDto addCustomerDto) {
        boardService.saveBoardCustomers(boardId, addCustomerDto.email());
    }

    @Operation(summary = "Get all boards")
    @ApiResponse(responseCode = "200", description = "List of boards")
    @GetMapping
    public List<BoardResponseDto> getBoards() {
        return boardService.getCustomersBoards();
    }

    @Operation(summary = "Delete board by id")
    @ApiResponse(responseCode = "200", description = "Board")
    @DeleteMapping("/{boardId}")
    public void deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
