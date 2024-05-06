package com.example.zapbites.Business.Security;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BusinessUserDetailsService implements UserDetailsService {

    private final BusinessRepository businessRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Business> optionalBusiness = businessRepository.findByEmail(email);
        Business business = optionalBusiness.orElseThrow(() -> new UsernameNotFoundException("Business with email " + email + " not found."));


        return new BusinessUserDetails(business);
    }
}