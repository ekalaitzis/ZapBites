package com.example.zapbites.Customer;

import com.example.zapbites.Customer.Exceptions.CustomerNotFoundException;
import com.example.zapbites.Customer.Exceptions.DuplicateCustomerException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        return optionalCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer createCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(createCustomer, HttpStatus.CREATED);
        } catch (DuplicateCustomerException e) {
            return new ResponseEntity<>("This Customer already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        try {
            Customer updateCustomer = customerService.updateCustomer(customer);
            return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}