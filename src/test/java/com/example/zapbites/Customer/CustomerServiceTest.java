package com.example.zapbites.Customer;

import com.example.zapbites.Customer.Exceptions.CustomerNotFoundException;
import com.example.zapbites.Customer.Exceptions.DuplicateCustomerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer1, customer2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setEmail("customer1@example.com");
        customer1.setPassword("password1");

        customer2 = new Customer();
        customer2.setId(2L);
        customer2.setEmail("customer2@example.com");
        customer2.setPassword("password2");
    }

    @Test
    void getAllCustomers() {
        List<Customer> customerList = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(customerList, result);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        Optional<Customer> result = customerService.getCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals(customer1, result.get());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void createCustomer() {
        when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(customer1.getPassword())).thenReturn("password1");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        Customer result = customerService.createCustomer(customer1);

        assertNotNull(result);
        assertEquals(customer1.getEmail(), result.getEmail());
        assertEquals("password1", result.getPassword());
        verify(customerRepository, times(1)).findByEmail(customer1.getEmail());
        verify(encoder, times(1)).encode(customer1.getPassword());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void createCustomer_DuplicateException() {
        when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(Optional.of(customer1));

        assertThrows(DuplicateCustomerException.class, () -> customerService.createCustomer(customer1));
        verify(customerRepository, times(1)).findByEmail(customer1.getEmail());
        verify(encoder, never()).encode(any());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void updateCustomer() {
        customer1.setPassword("newPassword");
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
        when(encoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        Customer result = customerService.updateCustomer(customer1);

        assertNotNull(result);
        assertEquals(customer1, result);
        assertEquals("encodedNewPassword", result.getPassword());
        verify(customerRepository, times(1)).findAll();
        verify(encoder, times(1)).encode("newPassword");
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_CustomerNotFoundException() {
        Customer nonExistingCustomer = new Customer();
        nonExistingCustomer.setId(3L);
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(nonExistingCustomer));
        verify(customerRepository, times(1)).findAll();
        verify(encoder, never()).encode(any());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void deleteCustomer() {
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }
}