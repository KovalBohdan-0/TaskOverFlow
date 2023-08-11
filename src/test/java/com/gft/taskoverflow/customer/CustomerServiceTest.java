package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.customer.dto.CustomerDto;
import com.gft.taskoverflow.registration.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExistsByEmail() {
        String email = "test@example.com";
        when(customerRepository.existsByEmail(email)).thenReturn(true);

        boolean result = customerService.existsByEmail(email);

        assertTrue(result);
    }


    @Test
    public void testGetCustomerByEmail() {
        String email = "test@example.com";
        Customer customer = new Customer();
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerByEmail(email);

        assertNotNull(result);
    }

    @Test
    public void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(customerId);

        assertNotNull(result);
    }

    @Test
    public void testSignUpCustomer() {
        RegistrationRequest request = new RegistrationRequest("test@example.com", "password");
        when(customerRepository.existsByEmail(request.email())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(request.password())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> customerService.signUpCustomer(request));
    }

    @Test
    public void testGetCurrentCustomer() {
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        CustomerDto result = customerService.getCurrentCustomer();

        assertNotNull(result);
        assertEquals(email, result.email());
    }

    @Test
    public void testGetCurrentCustomerEntity() {
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCurrentCustomerEntity();

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testCurrentCustomerContainsBoard() {
        Long boardId = 1L;
        Customer customer = new Customer();
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("email");
        when(customerRepository.findByEmail("email")).thenReturn(Optional.of(customer));
        when(customerRepository.containsBoardByIdAndCustomerId(boardId, customer.getId())).thenReturn(true);

        boolean result = customerService.currentCustomerContainsBoard(boardId);

        assertTrue(result);
    }
}