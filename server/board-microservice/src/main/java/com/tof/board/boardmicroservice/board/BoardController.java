package com.tof.board.boardmicroservice.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    @GetMapping("/board")
    public String getBoard() {
        return "Hello Board";
    }
}
