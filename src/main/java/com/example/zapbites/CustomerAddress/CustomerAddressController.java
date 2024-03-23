package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Business.BusinessController;
import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;
import com.example.zapbites.CustomerAddress.Exceptions.DuplicateCustomerAddressException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customer_address")
@Validated
public class CustomerAddressController {
    private static final Logger logger = LogManager.getLogger(BusinessController.class);

    private final CustomerAddressService customerAddressService;

    @Autowired
    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerAddress>> getAllCustomerAddresses() {
        List<CustomerAddress> customerAddresses = customerAddressService.getAllCustomerAddresses();
        return new ResponseEntity<>(customerAddresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerAddress> getCustomerAddressById(@PathVariable Long id) {
        Optional<CustomerAddress> optionalCustomerAddress = customerAddressService.getCustomerAddressById(id);
        return optionalCustomerAddress.map(customerAddress -> new ResponseEntity<>(customerAddress, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> createCustomerAddress(@Valid @RequestBody CustomerAddress customerAddress) {
            var createdCustomerAddress = customerAddressService.createCustomerAddress(customerAddress);
            return new ResponseEntity<>(createdCustomerAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerAddress> updateCustomerAddress(@RequestBody CustomerAddress customerAddress) {
            CustomerAddress updatedCustomerAddress = customerAddressService.updateCustomerAddress(customerAddress);
            return new ResponseEntity<>(updatedCustomerAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerAddressById(@PathVariable Long id) {
        customerAddressService.deleteCustomerAddressById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
