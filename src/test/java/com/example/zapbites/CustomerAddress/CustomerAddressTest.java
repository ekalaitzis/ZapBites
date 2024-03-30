package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CustomerAddressTest {

    @Test
    public void testCustomerAddressEntity() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L); // Assuming customer ID 1 exists
        CustomerAddress customerAddress = new CustomerAddress(1L, "123 Main St", new Point(1, 2),true, customer);

        // When - No action needed as this is just a simple entity test

        // Then
        assertNotNull(customerAddress.getId());
        assertEquals(1L, customerAddress.getId());
        assertEquals("123 Main St", customerAddress.getAddress());
        assertEquals(new Point(1, 2), customerAddress.getGeolocation());
        assertEquals(customer, customerAddress.getCustomer());
    }
}
