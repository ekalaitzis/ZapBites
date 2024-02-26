package com.example.zapbites.CustomerAddress;

import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;
import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
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
public class CustomerAddressServiceTest {

    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @InjectMocks
    private CustomerAddressService customerAddressService;

    @Test
    void getAllCustomerAddresses_ShouldReturnListOfCustomerAddresses() {
        List<CustomerAddress> customerAddressList = new ArrayList<>();
        customerAddressList.add(new CustomerAddress());
        customerAddressList.add(new CustomerAddress());
        when(customerAddressRepository.findAll()).thenReturn(customerAddressList);

        List<CustomerAddress> result = customerAddressService.getAllCustomerAddresses();

        assertEquals(2, result.size());
    }

    @Test
    void getCustomerAddressById_WithValidId_ShouldReturnCustomerAddress() {
        Long customerAddressId = 1L;
        CustomerAddress customerAddress = new CustomerAddress();
        when(customerAddressRepository.findById(customerAddressId)).thenReturn(Optional.of(customerAddress));

        Optional<CustomerAddress> result = customerAddressService.getCustomerAddressById(customerAddressId);

        assertTrue(result.isPresent());
        assertEquals(customerAddress, result.get());
    }

    @Test
    void getCustomerAddressById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long customerAddressId = 1L;
        when(customerAddressRepository.findById(customerAddressId)).thenReturn(Optional.empty());

        Optional<CustomerAddress> result = customerAddressService.getCustomerAddressById(customerAddressId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createCustomerAddress_WithValidCustomerAddress_ShouldReturnCreatedCustomerAddress() {
        CustomerAddress customerAddress = new CustomerAddress();
        when(customerAddressRepository.findById(any())).thenReturn(Optional.empty());
        when(customerAddressRepository.save(customerAddress)).thenReturn(customerAddress);

        CustomerAddress result = customerAddressService.createCustomerAddress(customerAddress);

        assertEquals(customerAddress, result);
    }

    @Test
    void createCustomerAddress_WithDuplicateId_ShouldThrowDuplicateBusinessException() {
        CustomerAddress customerAddress = new CustomerAddress();
        when(customerAddressRepository.findById(any())).thenReturn(Optional.of(new CustomerAddress()));

        assertThrows(DuplicateBusinessException.class, () -> customerAddressService.createCustomerAddress(customerAddress));
    }

    @Test
    void updateCustomerAddress_WithValidCustomerAddress_ShouldReturnUpdatedCustomerAddress() {
        CustomerAddress updatedCustomerAddress = new CustomerAddress();
        updatedCustomerAddress.setId(1L);
        // Mocking getAllCustomerAddresses() to return a list containing the updated customer address
        when(customerAddressService.getAllCustomerAddresses()).thenReturn(Collections.singletonList(updatedCustomerAddress));
        when(customerAddressRepository.save(updatedCustomerAddress)).thenReturn(updatedCustomerAddress);

        CustomerAddress result = customerAddressService.updateCustomerAddress(updatedCustomerAddress);

        assertEquals(updatedCustomerAddress, result);
    }

    @Test
    void updateCustomerAddress_WithNonExistingId_ShouldThrowCustomerAddressNotFoundException() {
        CustomerAddress updatedCustomerAddress = new CustomerAddress();
        // Mocking getAllCustomerAddresses() to return an empty list, simulating that the customer address does not exist
        when(customerAddressService.getAllCustomerAddresses()).thenReturn(Collections.emptyList());

        assertThrows(CustomerAddressNotFoundException.class, () -> customerAddressService.updateCustomerAddress(updatedCustomerAddress));
    }

    @Test
    void deleteCustomerAddressById_WithValidId_ShouldDeleteCustomerAddress() {
        Long customerAddressId = 1L;

        customerAddressService.deleteCustomerAddressById(customerAddressId);

        verify(customerAddressRepository, times(1)).deleteById(customerAddressId);
    }

    @Test
    void deleteCustomerAddressById_WithNonExistingId_ShouldThrowCustomerAddressNotFoundException() {
        Long customerAddressId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(customerAddressRepository).deleteById(customerAddressId);

        assertThrows(CustomerAddressNotFoundException.class, () -> customerAddressService.deleteCustomerAddressById(customerAddressId));
    }
}
