package com.example.zapbites.CustomerAddress;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerAddressService {
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressService(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    public List<CustomerAddress> getAllCustomerAddresses() {
        return customerAddressRepository.findAll();
    }

    public Optional<CustomerAddress> getCustomerAddressById(Long customerAddressId) {
        return customerAddressRepository.findById(customerAddressId);
    }

    public CustomerAddress createCustomerAddress(CustomerAddress customerAddress) {
        return customerAddressRepository.save(customerAddress);
    }

    public CustomerAddress updateCustomerAddress(CustomerAddress updatedCustomerAddress) {
        try {
            return customerAddressRepository.save(updatedCustomerAddress);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("CustomerAddress with id " + updatedCustomerAddress.getId() + " not found.", e);
        }
    }

    public void deleteCustomerAddressById(Long id) {
        try {
            customerAddressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("CustomerAddress with id " + id + " not found", e);
        }
    }
}