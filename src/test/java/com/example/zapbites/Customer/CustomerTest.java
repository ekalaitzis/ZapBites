package com.example.zapbites.Customer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CustomerTest {

    @Test
    public void testCustomerEntity() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("password123");
        customer.setTelephone("1234567890");

        // When - No action needed as this is just a simple entity test

        // Then
        assertNotNull(customer.getId());
        assertEquals(1L, customer.getId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("password123", customer.getPassword());
        assertEquals("1234567890", customer.getTelephone());
    }
}
