package com.example.zapbites.Customer;

import com.example.zapbites.Customer.Exceptions.CustomerNotFoundException;
import com.example.zapbites.Customer.Exceptions.DuplicateCustomerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        customerList.add(new Customer());
        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
    }

    @Test
    void getCustomerById_WithValidId_ShouldReturnCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void getCustomerById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createCustomer_WithValidCustomer_ShouldReturnCreatedCustomer() {
        Customer customer = new Customer();
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertEquals(customer, result);
    }

    @Test
    void createCustomer_WithDuplicateEmail_ShouldThrowDuplicateBusinessException() {
        Customer customer = new Customer();
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));

        assertThrows(DuplicateCustomerException.class, () -> customerService.createCustomer(customer));
    }

    @Test
    void updateCustomer_WithValidCustomer_ShouldReturnUpdatedCustomer() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        // Mocking getAllCustomers() to return a list containing the updated customer
        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(updatedCustomer));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(updatedCustomer);

        assertEquals(updatedCustomer, result);
    }

    @Test
    void updateCustomer_WithNonExistingId_ShouldThrowCustomerNotFoundException() {
        Customer updatedCustomer = new Customer();
        // Mocking getAllCustomers() to return an empty list, simulating that the customer does not exist
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(updatedCustomer));
    }

    @Test
    void deleteCustomer_WithValidId_ShouldDeleteCustomer() {
        Long customerId = 1L;

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void deleteCustomer_WithNonExistingId_ShouldThrowCustomerNotFoundException() {
        Long customerId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(customerRepository).deleteById(customerId);

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(customerId));
    }
}
