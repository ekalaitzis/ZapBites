package com.example.zapbites.BusinessTest;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BusinessRepositoryTest {

    @Autowired
    private BusinessRepository businessRepository;

    @Test
    void testSaveBusiness() {

        Business business = new Business();
        business.setCompanyName("Test Company");
        business.setEmail("test@example.com");
        business.setPassword("password123");
        business.setTelephone("1234567890");
        business.setTaxIdNumber("1234567890");

        Business savedBusiness = businessRepository.save(business);

        assertNotNull(savedBusiness.getId());
        assertEquals("Test Company", savedBusiness.getCompanyName());
        assertEquals("test@example.com", savedBusiness.getEmail());
        assertEquals("password123", savedBusiness.getPassword());
        assertEquals("1234567890", savedBusiness.getTelephone());
        assertEquals("1234567890", savedBusiness.getTaxIdNumber());
    }

    @Test
    void testFindBusinessById() {
        Business business = new Business();
        business.setCompanyName("Test Company");
        business.setEmail("test@example.com");
        business.setPassword("password123");
        business.setTelephone("1234567890");
        business.setTaxIdNumber("1234567890");
        Business savedBusiness = businessRepository.save(business);

        Optional<Business> foundBusiness = businessRepository.findById(savedBusiness.getId());

        assertTrue(foundBusiness.isPresent());
        assertEquals(savedBusiness, foundBusiness.get());
    }

    @Test
    void testUpdateBusiness() {
        Business business = new Business();
        business.setCompanyName("Test Company");
        business.setEmail("test@example.com");
        business.setPassword("password123");
        business.setTelephone("1234567890");
        business.setTaxIdNumber("1234567890");
        Business savedBusiness = businessRepository.save(business);

        savedBusiness.setCompanyName("Updated Company");

        Business updatedBusiness = businessRepository.save(savedBusiness);

        assertEquals(savedBusiness.getId(), updatedBusiness.getId());
        assertEquals("Updated Company", updatedBusiness.getCompanyName());
    }

    @Test
    void testDeleteBusiness() {
        Business business = new Business();
        business.setCompanyName("Test Company");
        business.setEmail("test@example.com");
        business.setPassword("password123");
        business.setTelephone("1234567890");
        business.setTaxIdNumber("1234567890");
        Business savedBusiness = businessRepository.save(business);

        businessRepository.deleteById(savedBusiness.getId());

        assertFalse(businessRepository.findById(savedBusiness.getId()).isPresent());
    }
}
