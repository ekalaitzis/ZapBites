package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
    Optional<CustomerAddress> findByAddress(String address);
    @Query("SELECT ca FROM CustomerAddress ca WHERE ca.customer = :customer")
    List<CustomerAddress> findAllByCustomer(Customer customer);
}
