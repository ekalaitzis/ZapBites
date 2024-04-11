package com.example.zapbites.CustomerAddress;

import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressService {

    List<CustomerAddress> getAllCustomerAddresses();

    Optional<CustomerAddress> getCustomerAddressById(Long customerAddressId);

    CustomerAddress createCustomerAddress(CustomerAddress customerAddress);

    CustomerAddress updateCustomerAddress(CustomerAddress updatedCustomerAddress) throws CustomerAddressNotFoundException;

    void deleteCustomerAddressById(Long id);
}
