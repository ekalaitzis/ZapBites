package com.example.zapbites.Customer;

import com.example.zapbites.Customer.Exceptions.CustomerNotFoundException;
import com.example.zapbites.Customer.Exceptions.DuplicateCustomerException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private BCryptPasswordEncoder encoder;


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        String email = customer.getEmail();
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);

        if (existingCustomer.isPresent()) {
            throw new DuplicateCustomerException("Customer with email: " + email + " already exists.");
        }
        customer.setPassword(encoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        List<Customer> allCustomers = getAllCustomers();

        if (allCustomers.stream().anyMatch(c -> c.getId().equals(updatedCustomer.getId()))) {
            updatedCustomer.setPassword(encoder.encode(updatedCustomer.getPassword()));
            return customerRepository.save(updatedCustomer);
        } else {
            throw new CustomerNotFoundException("Customer with id: " + updatedCustomer.getId() + " not found.");
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
