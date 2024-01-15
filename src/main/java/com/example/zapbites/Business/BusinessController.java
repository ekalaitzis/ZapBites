package com.example.zapbites.Business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public ResponseEntity<List<Business>> getAllBusinesses() {
        List<Business> businesses = businessService.getAllBusinesses();
        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusinessById(@PathVariable Long id) {
        Optional<Business> toBeSearchedBusiness = businessService.getBusinessById(id);
        if (toBeSearchedBusiness.isPresent()) {
            return new ResponseEntity<>(toBeSearchedBusiness.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Business> createBusiness(@RequestBody Business business) {
        Business createdBusiness = businessService.createBusiness(business);
        return new ResponseEntity<>(createdBusiness, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Business> updateBusiness(@PathVariable Long id, @RequestBody Business business) {
        Business updatedBusiness = businessService.updateBusiness(id, business);
        if (updatedBusiness != null) {
            return new ResponseEntity<>(updatedBusiness, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessById(@PathVariable Long id) {
        if (businessService.deleteBusinessById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
