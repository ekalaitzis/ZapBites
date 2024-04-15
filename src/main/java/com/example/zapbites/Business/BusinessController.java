package com.example.zapbites.Business;

import com.example.zapbites.Business.Security.BusinessUserDetails;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/business")
@Validated
public class BusinessController {
    private static final Logger logger = LogManager.getLogger(BusinessController.class);

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public ResponseEntity<List<Business>> getAllBusinesses() {
        List<Business> businesses = businessService.getAllBusinesses();
        logger.info("all businesses: {}", businesses);

        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusinessById(@PathVariable Long id) {
        Optional<Business> optionalBusiness = businessService.getBusinessById(id);
        return optionalBusiness.map(business -> new ResponseEntity<>(business, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> createBusiness(@Valid @RequestBody Business business) {
        Business createdBusiness = businessService.createBusiness(business);
        return new ResponseEntity<>(createdBusiness, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(@PathVariable Long id, @RequestBody Business business, Authentication authentication) {
        BusinessUserDetails userDetails = (BusinessUserDetails) authentication.getPrincipal();
        Business currentBusiness = userDetails.getBusiness();

        if (currentBusiness.getId().equals(id)) {
            Business updatedBusiness = businessService.updateBusiness(business);
            return new ResponseEntity<>(updatedBusiness, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessById(@PathVariable Long id) {
        businessService.deleteBusinessById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
