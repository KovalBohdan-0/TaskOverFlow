package com.gft.taskoverflow.board;

import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.customer.CustomerUserDetailsService;
import com.gft.taskoverflow.exception.BoardNotFoundException;
import com.gft.taskoverflow.exception.ResourceNotFoundException;
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

    public void saveBoard(BoardDto boardDto) {
        Board board = boardMapper.mapToEntity(boardDto);
        board.setCustomers(Set.of(customerUserDetailsService.getCurrentCustomer()));
        boardRepository.save(board);
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));
    }
}
