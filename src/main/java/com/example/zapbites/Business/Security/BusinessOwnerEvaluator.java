package com.example.zapbites.Business.Security;


import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BusinessOwnerEvaluator {
    @Autowired
    private BusinessService businessService;

    public boolean checkForOwnerById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Check if the authenticated user is the owner of the business with the given id
        Optional<Business> optionalBusiness = businessService.getBusinessById(id);

        return optionalBusiness
                .filter(business -> business.getEmail().equals(username))
                .isPresent();
    }

    public boolean checkForOwnerByBusiness(Business business) {
        Long id = business.getId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();

        // Check if the authenticated user is the owner of the business with the given id
        Optional<Business> optionalBusiness = businessService.getBusinessById(id);

        return optionalBusiness
                .filter(b -> b.getId().equals(id))
                .isPresent();
    }

}
