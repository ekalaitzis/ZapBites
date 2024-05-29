package com.example.zapbites.config.security;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessRepository;
import com.example.zapbites.Business.Security.BusinessUserDetails;
import com.example.zapbites.Customer.Customer;
import com.example.zapbites.Customer.CustomerRepository;
import com.example.zapbites.Customer.Security.CustomerUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Business> optionalBusiness = businessRepository.findByEmail(email);
        if (optionalBusiness.isPresent()) {
            return new BusinessUserDetails(optionalBusiness.get());
        }

        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            return new CustomerUserDetails(optionalCustomer.get());
        }

        throw new UsernameNotFoundException(String.format("User with email %s not found.", email));
    }
}