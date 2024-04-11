package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;
import com.example.zapbites.CustomerAddress.Exceptions.DuplicateCustomerAddressException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerAddressServiceImpl implements CustomerAddressService {
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressServiceImpl(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    @Override
    public List<CustomerAddress> getAllCustomerAddresses() {
        return customerAddressRepository.findAll();
    }

    @Override
    public Optional<CustomerAddress> getCustomerAddressById(Long customerAddressId) {
        return customerAddressRepository.findById(customerAddressId);
    }

    @Override
    public CustomerAddress createCustomerAddress(CustomerAddress addressToBeCreated) {
        if (findExistingCustomerAddress(addressToBeCreated).isPresent()) {
            throw new DuplicateCustomerAddressException(addressToBeCreated.getAddress() + " address already exists.");
        }

        if (addressToBeCreated.isPrimary()) {
            setPrimaryToFalse(getAllAddressesFromCustomer(addressToBeCreated));
            customerAddressRepository.saveAll(getAllAddressesFromCustomer(addressToBeCreated));
        }
        addressToBeCreated.setPrimary(true);
        return customerAddressRepository.save(addressToBeCreated);
    }

    @Override
    public CustomerAddress updateCustomerAddress(CustomerAddress addressToBeUpdated) {
        if (!customerAddressAlreadyExists(addressToBeUpdated)) {
            throw new CustomerAddressNotFoundException("Customer address doesn't exist.");
        }

        if (addressToBeUpdated.isPrimary()) {
            setPrimaryToFalse(getAllAddressesFromCustomer(addressToBeUpdated));
            customerAddressRepository.saveAll(getAllAddressesFromCustomer(addressToBeUpdated));
        } return customerAddressRepository.save(addressToBeUpdated);
    }

    @Override
    public void deleteCustomerAddressById(Long id) {
        customerAddressRepository.deleteById(id);
    }

    private List<CustomerAddress> getAllAddressesFromCustomer(CustomerAddress customerAddress) {
        Customer customer = customerAddress.getCustomer();
        return  customerAddressRepository.findAllByCustomer(customer);
    }
    private void setPrimaryToFalse(List<CustomerAddress> addressesOfTheCustomer) {
        addressesOfTheCustomer.stream()
                .filter(CustomerAddress::isPrimary)
                .findFirst()
                .ifPresent(primaryAddress -> primaryAddress
                        .setPrimary(false));
    }

    private boolean customerAddressAlreadyExists(CustomerAddress customerAddress) {
        return customerAddress.getId() != null;
    }


    private Optional<CustomerAddress> findExistingCustomerAddress(CustomerAddress address) {
        return  customerAddressRepository.findByAddress(address.getAddress());
    }

}