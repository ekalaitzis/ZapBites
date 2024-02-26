package com.example.zapbites.Order;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Customer.Customer;
import com.example.zapbites.CustomerAddress.CustomerAddress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderTest {

    @Test
    public void testOrderEntity() {
        // Given
        Business business = new Business();
        business.setId(1L); // Assuming business ID 1 exists

        Customer customer = new Customer();
        customer.setId(1L); // Assuming customer ID 1 exists

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(1L); // Assuming customer address ID 1 exists

        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        Order order = new Order(1L, business, customer, customerAddress, BigDecimal.valueOf(100.00), createdAt);

        // When - No action needed as this is just a simple entity test

        // Then
        assertNotNull(order.getId());
        assertEquals(1L, order.getId());
        assertEquals(business, order.getBusinessId());
        assertEquals(customer, order.getCustomerId());
        assertEquals(customerAddress, order.getCustomerAddressId());
        assertEquals(BigDecimal.valueOf(100.00), order.getTotalPrice());
        assertEquals(createdAt, order.getCreatedAt());
    }
}
