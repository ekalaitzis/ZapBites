package com.example.zapbites.Business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BusinessTest {

    @Test
    public void testBusinessEntity() {
        Long expectedId = 1L;
        String expectedCompanyName = "Test Company";
        String expectedEmail = "test@example.com";
        String expectedPassword = "testPassword";
        String expectedTelephone = "1234567890";
        String expectedTaxIdNumber = "ABC123";

        Business business = new Business(expectedId, expectedCompanyName, expectedEmail, expectedPassword, expectedTelephone, expectedTaxIdNumber);

        assertNotNull(business);
        assertEquals(expectedId, business.getId());
        assertEquals(expectedCompanyName, business.getCompanyName());
        assertEquals(expectedEmail, business.getEmail());
        assertEquals(expectedPassword, business.getPassword());
        assertEquals(expectedTelephone, business.getTelephone());
        assertEquals(expectedTaxIdNumber, business.getTaxIdNumber());
    }
}

