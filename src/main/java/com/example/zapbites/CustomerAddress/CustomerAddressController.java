package com.example.zapbites.CustomerAddress;

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
@RequestMapping("customer_address")
@Validated
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @Autowired
    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')") //this is not secured properly as it is not going to be used in production
    public ResponseEntity<List<CustomerAddress>> getAllCustomerAddresses() {
        List<CustomerAddress> customerAddresses = customerAddressService.getAllCustomerAddresses();
        return new ResponseEntity<>(customerAddresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@customerOwnerEvaluator.checkForOwnerByCustomerAddressId(#id)")
    public ResponseEntity<CustomerAddress> getCustomerAddressById(@PathVariable Long id) {
        Optional<CustomerAddress> optionalCustomerAddress = customerAddressService.getCustomerAddressById(id);
        return optionalCustomerAddress.map(customerAddress -> new ResponseEntity<>(customerAddress, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCustomerAddress(@Valid @RequestBody CustomerAddress customerAddress) {
            var createdCustomerAddress = customerAddressService.createCustomerAddress(customerAddress);
            return new ResponseEntity<>(createdCustomerAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@customerOwnerEvaluator.checkForOwnerByCustomerAddress(#customerAddress)")
    public ResponseEntity<CustomerAddress> updateCustomerAddress(@RequestBody CustomerAddress customerAddress) {
            CustomerAddress updatedCustomerAddress = customerAddressService.updateCustomerAddress(customerAddress);
            return new ResponseEntity<>(updatedCustomerAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customerOwnerEvaluator.checkForOwnerByCustomerAddressId(#id)")
    public ResponseEntity<Void> deleteCustomerAddressById(@PathVariable Long id) {
        customerAddressService.deleteCustomerAddressById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
