package com.gft.taskoverflow.board;

import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.customer.CustomerUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @Mock
    private CustomerUserDetailsService customerUserDetailsService;

    @Mock
    private BoardMapper boardMapper;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCustomersBoards() {
        String customerEmail = "test@example.com";
        List<Board> mockBoards = new ArrayList<>();
        when(customerUserDetailsService.getCurrentCustomerEmail()).thenReturn(customerEmail);
        when(boardRepository.findAllByCustomersEmail(customerEmail)).thenReturn(mockBoards);
        when(boardMapper.mapToResponseDto(any())).thenReturn(new BoardResponseDto(1L, "title"));

        List<BoardResponseDto> result = boardService.getCustomersBoards();

        assertNotNull(result);
    }

    @Test
    public void testSaveBoardCustomers() {
        Long boardId = 1L;
        String email = "test@example.com";
        Board board = new Board();
        Customer customer = new Customer();
        customer.setId(1L);
        HashSet<Customer> customers = new HashSet<>();
        customers.add(customer);
        board.setCustomers(customers);
        when(customerUserDetailsService.getCustomerByEmail(email)).thenReturn(customer);
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        boardService.saveBoardCustomers(boardId, email);

        assertTrue(board.getCustomers().contains(customer));
    }

    @Test
    public void testSaveBoard() {
        Board board = new Board();
        Customer customer = new Customer();
        when(customerUserDetailsService.getCurrentCustomer()).thenReturn(customer);

        boardService.saveBoard(board);

        assertTrue(board.getCustomers().contains(customer));
    }

    @Test
    public void testGetBoardById() {
        Long boardId = 1L;
        Board board = new Board();
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        Board result = boardService.getBoardById(boardId);

        assertNotNull(result);
    }
}