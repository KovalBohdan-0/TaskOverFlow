package com.gft.taskoverflow.board;

import com.gft.taskoverflow.customer.CustomerUserDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @Mock
    private CustomerUserDetailsService customerUserDetailsService;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private BoardService boardService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

//    @Test
//    public void testGetCustomersBoards() {
//        String email = "test@example.com";
//        List<Board> boards = List.of(new Board(), new Board());
//
//        when(customerUserDetailsService.getCurrentCustomerEmail()).thenReturn(email);
//        when(boardRepository.findAllByCustomersEmail(email)).thenReturn(boards);
//
//        List<Board> result = boardService.getCustomersBoards();
//
//        assertEquals(2, result.size());
//
//        verify(customerUserDetailsService, times(1)).getCurrentCustomerEmail();
//        verify(boardRepository, times(1)).findAllByCustomersEmail(email);
//    }

//    @Test
//    public void testSaveBoardCustomers() {
//        Long boardId = 1L;
//        String email = "test@example.com";
//
//        boardService.saveBoardCustomers(boardId, email);
//
//        verify(boardRepository, times(1)).findAllByCustomersEmail(email, boardId);
//    }
}