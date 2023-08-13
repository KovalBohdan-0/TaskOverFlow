package com.gft.taskoverflow.board;

import com.gft.taskoverflow.board.dto.BoardResponseDto;
import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.customer.CustomerUserDetailsService;
import com.gft.taskoverflow.exception.BoardNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Data
public class BoardService {
    private final BoardRepository boardRepository;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final BoardMapper boardMapper;

    public List<BoardResponseDto> getCustomersBoards() {
        return boardRepository.findAllByCustomersEmail(customerUserDetailsService.getCurrentCustomerEmail())
                .stream()
                .map(boardMapper::mapToResponseDto)
                .toList();
    }

    public void saveBoardCustomers(Long boardId, String email) {
        Customer customer = customerUserDetailsService.getCustomerByEmail(email);
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));
        board.getCustomers().add(customer);
        boardRepository.save(board);
    }

    public Long saveBoard(Board board) {
        board.setCustomers(Set.of(customerUserDetailsService.getCurrentCustomer()));
        boardRepository.save(board);
        return board.getId();
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
