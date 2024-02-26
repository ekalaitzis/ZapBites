package com.example.zapbites.Customer;

import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import com.example.zapbites.Customer.Exceptions.CustomerNotFoundException;
import com.example.zapbites.Customer.Exceptions.DuplicateCustomerException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.findById(customer.getId()).isPresent()) {
            throw new DuplicateCustomerException("customer with email " + customer.getEmail() + " already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer updatedCustomer) {
        List<Customer> allCustomers = getAllCustomers();

        if (allCustomers.stream().anyMatch(c -> c.getId().equals(updatedCustomer.getId()))) {
            return customerRepository.save(updatedCustomer);
        } else {
            throw new CustomerNotFoundException("Customer with id " + updatedCustomer.getId() + " not found.");
        }
    }

    public void deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomerNotFoundException("Customer wih id " + id + " not found", e);
        }
    }
}
