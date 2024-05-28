package com.example.zapbites.Customer.Security;


import com.example.zapbites.Customer.Customer;
import com.example.zapbites.Customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerOwnerEvaluator {

    private CustomerService customerService;

    public boolean checkForOwnerById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Check if the authenticated user is the owner of the customer with the given id
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);

        return optionalCustomer
                .filter(customer -> customer.getEmail().equals(username))
                .isPresent();
    }

    public boolean checkForOwnerByCustomer(Customer customer) {
        return checkForOwnerById(customer.getId());
    }

}
