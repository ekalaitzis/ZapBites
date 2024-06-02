package com.example.zapbites.Customer.Security;


import com.example.zapbites.Customer.Customer;
import com.example.zapbites.Customer.CustomerService;
import com.example.zapbites.CustomerAddress.CustomerAddress;
import com.example.zapbites.CustomerAddress.CustomerAddressService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerOwnerEvaluator {

    private CustomerService customerService;
    private CustomerAddressService customerAddressService;



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

    public boolean checkForOwnerByCustomerAddress(CustomerAddress customerAddress) {
        return checkForOwnerByCustomerAddressId(customerAddress.getId());

    }

    public boolean checkForOwnerByCustomerAddressId(Long id) {
        Optional<CustomerAddress> optionalCustomerAddress = customerAddressService.getCustomerAddressById(id);
        if (optionalCustomerAddress.isPresent()) {
            Long customerId = optionalCustomerAddress
                    .map(customerAddress -> customerAddress.getCustomer().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer ID not found"));

            return checkForOwnerById(customerId);
        }
        return false;
    }
}