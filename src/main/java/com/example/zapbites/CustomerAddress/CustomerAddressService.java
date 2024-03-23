package com.example.zapbites.CustomerAddress;

import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;
import com.example.zapbites.CustomerAddress.Exceptions.DuplicateCustomerAddressException;
import jakarta.transaction.Transactional;
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
        String address = customerAddress.getAddress();
        Optional<CustomerAddress> existingCustomerAddress = customerAddressRepository.findByAddress(address);

        if (existingCustomerAddress.isPresent()) {
            throw new DuplicateCustomerAddressException(address + " address already exists.");
        }
        return customerAddressRepository.save(customerAddress);
    }

    public CustomerAddress updateCustomerAddress(CustomerAddress updatedCustomerAddress) {
        List<CustomerAddress> allCustomerAddresses = getAllCustomerAddresses();

        if (allCustomerAddresses.stream().anyMatch(c -> c.getId().equals(updatedCustomerAddress.getId()))) {
            return customerAddressRepository.save(updatedCustomerAddress);
        } else {
            throw new CustomerAddressNotFoundException("Customer address doesn't exist.");
        }
    }

    public void deleteCustomerAddressById(Long id) {
        customerAddressRepository.deleteById(id);
    }
}