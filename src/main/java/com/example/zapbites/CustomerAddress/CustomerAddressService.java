package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;
import jakarta.transaction.Transactional;
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
        List<CustomerAddress> allCustomerAddresses = getAllCustomerAddresses();

        if (allCustomerAddresses.stream().anyMatch(c -> c.getId().equals(updatedCustomerAddress.getId()))) {
            return customerAddressRepository.save(updatedCustomerAddress);
        } else {
            throw new CustomerAddressNotFoundException("CustomerAddress with id " + updatedCustomerAddress.getId() + " not found.");
        }
    }

    public void deleteCustomerAddressById(Long id) {
        try {
            customerAddressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerAddressNotFoundException("CustomerAddress with id " + id + " not found", e);
        }
    }
}