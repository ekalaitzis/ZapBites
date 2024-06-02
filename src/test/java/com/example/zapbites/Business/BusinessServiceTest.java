package com.example.zapbites.Business;

import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BusinessServiceTest {

    @Mock
    private BusinessRepository businessRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private BusinessServiceImpl businessService;

    private Business business1, business2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        business1 = new Business();
        business1.setId(1L);
        business1.setEmail("business1@example.com");
        business1.setPassword("password1");

        business2 = new Business();
        business2.setId(2L);
        business2.setEmail("business2@example.com");
        business2.setPassword("password2");
    }

    @Test
    void getAllBusinesses() {
        List<Business> businessList = Arrays.asList(business1, business2);
        when(businessRepository.findAll()).thenReturn(businessList);

        List<Business> result = businessService.getAllBusinesses();

        assertEquals(businessList, result);
        verify(businessRepository, times(1)).findAll();
    }

    @Test
    void getBusinessById() {
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business1));

        Optional<Business> result = businessService.getBusinessById(1L);

        assertTrue(result.isPresent());
        assertEquals(business1, result.get());
        verify(businessRepository, times(1)).findById(1L);
    }

    @Test
    void createBusiness() {
        when(businessRepository.findByEmail(business1.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(business1.getPassword())).thenReturn("password1");
        when(businessRepository.save(any(Business.class))).thenReturn(business1);

        Business result = businessService.createBusiness(business1);

        assertNotNull(result);
        assertEquals(business1.getEmail(), result.getEmail());
        assertEquals("password1", result.getPassword());
        verify(businessRepository, times(1)).findByEmail(business1.getEmail());
        verify(encoder, times(1)).encode(business1.getPassword());
        verify(businessRepository, times(1)).save(any(Business.class));
    }

    @Test
    void createBusiness_DuplicateException() {
        when(businessRepository.findByEmail(business1.getEmail())).thenReturn(Optional.of(business1));

        assertThrows(DuplicateBusinessException.class, () -> businessService.createBusiness(business1));
        verify(businessRepository, times(1)).findByEmail(business1.getEmail());
        verify(encoder, never()).encode(any());
        verify(businessRepository, never()).save(any());
    }

    @Test
    void updateBusiness() {
        business1.setPassword("newPassword");
        when(encoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(businessRepository.save(any(Business.class))).thenReturn(business1);

        Business result = businessService.updateBusiness(business1);

        assertNotNull(result);
        assertEquals(business1, result);
        assertEquals("encodedNewPassword", result.getPassword());
        verify(encoder, times(1)).encode("newPassword");
        verify(businessRepository, times(1)).save(any(Business.class));
    }

    @Test
    void deleteBusinessById() {
        businessService.deleteBusinessById(1L);
        verify(businessRepository, times(1)).deleteById(1L);
    }
}
