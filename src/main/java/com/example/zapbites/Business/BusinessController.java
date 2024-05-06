package com.example.zapbites.Business;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/business")
@Validated
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BUSINESS')")
    public ResponseEntity<List<Business>> getAllBusinesses() {
        List<Business> businesses = businessService.getAllBusinesses();
        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerById(#id)")
    public ResponseEntity<Business> getBusinessById(@PathVariable Long id) {
        Optional<Business> optionalBusiness = businessService.getBusinessById(id);
        return optionalBusiness.map(business -> new ResponseEntity<>(business, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createBusiness(@Valid @RequestBody Business business) {
        Business createdBusiness = businessService.createBusiness(business);
        return new ResponseEntity<>(createdBusiness, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByBusiness(#business)")
    public ResponseEntity<Business> updateBusiness(@RequestBody Business business) {
        Business updatedBusiness = businessService.updateBusiness(business);
        return new ResponseEntity<>(updatedBusiness, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerById(#id)")
    public ResponseEntity<Void> deleteBusinessById(@PathVariable Long id) {
        businessService.deleteBusinessById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
