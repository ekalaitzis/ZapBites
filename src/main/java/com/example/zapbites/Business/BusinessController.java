package com.example.zapbites.Business;

import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        try {
            Business createdBusiness = businessService.createBusiness(business);
            return new ResponseEntity<>(createdBusiness, HttpStatus.CREATED);
        } catch (DuplicateBusinessException e) {
            return new ResponseEntity<>("Business with this email already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(@RequestBody Business business) {
        try {
            Business updatedBusiness = businessService.updateBusiness(business);
            return new ResponseEntity<>(updatedBusiness, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessById(@PathVariable Long id) {
        businessService.deleteBusinessById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
