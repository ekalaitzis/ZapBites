package com.example.zapbites.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer updatedCustomer);

    void deleteCustomer(Long id);

}
