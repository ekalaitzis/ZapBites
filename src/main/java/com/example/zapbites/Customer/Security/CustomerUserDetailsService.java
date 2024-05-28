package com.example.zapbites.Customer.Security;

import com.example.zapbites.Customer.Customer;
import com.example.zapbites.Customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        Customer customer = optionalCustomer.orElseThrow(() -> new UsernameNotFoundException("Customer with email " + email + " not found."));


        return new CustomerUserDetails(customer);
    }
}