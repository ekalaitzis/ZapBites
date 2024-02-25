package com.example.zapbites.Customer;

import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import com.example.zapbites.Customer.Exceptions.CustomerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
            throw new DuplicateBusinessException("customer with email " + customer.getEmail() + " already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (DataAccessException e) {
            throw new CustomerNotFoundException("Customer wih id " + customer.getId() + " not found");
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
