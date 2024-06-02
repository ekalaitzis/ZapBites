package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;
import com.example.zapbites.CustomerAddress.Exceptions.DuplicateCustomerAddressException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerAddressServiceTest {
    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @InjectMocks
    private CustomerAddressServiceImpl customerAddressService;

    private CustomerAddress customerAddress1, customerAddress2;

    private Customer customer1, customer2;
    private Customer customer;

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

        customerAddress1 = new CustomerAddress();
        customerAddress1.setId(1L);
        customerAddress1.setAddress("Address 1");
        customerAddress1.setCustomer(customer);
        customerAddress1.setPrimary(true);

        customerAddress2 = new CustomerAddress();
        customerAddress2.setId(2L);
        customerAddress2.setAddress("Address 2");
        customerAddress2.setCustomer(customer);
        customerAddress2.setPrimary(false);
    }

    @Test
    void getAllCustomerAddresses() {
        List<CustomerAddress> addressList = Arrays.asList(customerAddress1, customerAddress2);
        when(customerAddressRepository.findAll()).thenReturn(addressList);

        List<CustomerAddress> result = customerAddressService.getAllCustomerAddresses();

        assertEquals(addressList, result);
        verify(customerAddressRepository, times(1)).findAll();
    }

    @Test
    void getCustomerAddressById() {
        when(customerAddressRepository.findById(1L)).thenReturn(Optional.of(customerAddress1));

        Optional<CustomerAddress> result = customerAddressService.getCustomerAddressById(1L);

        assertTrue(result.isPresent());
        assertEquals(customerAddress1, result.get());
        verify(customerAddressRepository, times(1)).findById(1L);
    }

    @Test
    void createCustomerAddress() {
        when(customerAddressRepository.findByAddress(customerAddress1.getAddress())).thenReturn(Optional.empty());
        when(customerAddressRepository.findAllByCustomer(customer)).thenReturn(Arrays.asList(customerAddress2)).thenReturn(Arrays.asList(customerAddress2));
        when(customerAddressRepository.save(any(CustomerAddress.class))).thenReturn(customerAddress1);

        CustomerAddress result = customerAddressService.createCustomerAddress(customerAddress1);

        assertNotNull(result);
        assertEquals(customerAddress1, result);
        assertTrue(result.isPrimary());
        verify(customerAddressRepository, times(1)).findByAddress(customerAddress1.getAddress());
        verify(customerAddressRepository, times(2)).findAllByCustomer(customer);
        verify(customerAddressRepository, times(1)).save(any(CustomerAddress.class));
    }

    @Test
    void createCustomerAddress_DuplicateException() {
        when(customerAddressRepository.findByAddress(customerAddress1.getAddress())).thenReturn(Optional.of(customerAddress1));

        assertThrows(DuplicateCustomerAddressException.class, () -> customerAddressService.createCustomerAddress(customerAddress1));
        verify(customerAddressRepository, times(1)).findByAddress(customerAddress1.getAddress());
        verify(customerAddressRepository, never()).findAllByCustomer(any());
        verify(customerAddressRepository, never()).save(any());
    }

    @Test
    void updateCustomerAddress() {
        when(customerAddressRepository.findAllByCustomer(customer)).thenReturn(Arrays.asList(customerAddress1, customerAddress2)).thenReturn(Arrays.asList(customerAddress1, customerAddress2));
        when(customerAddressRepository.save(any(CustomerAddress.class))).thenReturn(customerAddress1);

        CustomerAddress result = customerAddressService.updateCustomerAddress(customerAddress1);

        assertNotNull(result);
        assertEquals(customerAddress1, result);
        assertFalse(result.isPrimary()); // No need to change this assertion
        verify(customerAddressRepository, times(2)).findAllByCustomer(customer);
        verify(customerAddressRepository, times(1)).save(any(CustomerAddress.class));
    }

    @Test
    void updateCustomerAddress_CustomerAddressNotFoundException() {
        customerAddress1.setId(null);

        assertThrows(CustomerAddressNotFoundException.class, () -> customerAddressService.updateCustomerAddress(customerAddress1));
        verify(customerAddressRepository, never()).findAllByCustomer(any());
        verify(customerAddressRepository, never()).save(any());
    }

    @Test
    void deleteCustomerAddressById() {
        customerAddressService.deleteCustomerAddressById(1L);
        verify(customerAddressRepository, times(1)).deleteById(1L);
    }
}