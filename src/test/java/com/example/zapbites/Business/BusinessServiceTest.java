package com.example.zapbites.Business;

import com.example.zapbites.Business.Exceptions.BusinessNotFoundException;
import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessServiceTest {

    @Mock
    private BusinessRepository businessRepository;

    @InjectMocks
    private BusinessService businessService;

    @Test
    void getAllBusinesses_ShouldReturnListOfBusinesses() {
        List<Business> businessList = new ArrayList<>();
        businessList.add(new Business());
        businessList.add(new Business());
        when(businessRepository.findAll()).thenReturn(businessList);

        List<Business> result = businessService.getAllBusinesses();

        assertEquals(2, result.size());
    }

    @Test
    void getBusinessById_WithValidId_ShouldReturnBusiness() {
        Long businessId = 1L;
        Business business = new Business();
        when(businessRepository.findById(businessId)).thenReturn(Optional.of(business));

        Optional<Business> result = businessService.getBusinessById(businessId);

        assertTrue(result.isPresent());
        assertEquals(business, result.get());
    }

    @Test
    void getBusinessById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long businessId = 1L;
        when(businessRepository.findById(businessId)).thenReturn(Optional.empty());

        Optional<Business> result = businessService.getBusinessById(businessId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createBusiness_WithValidBusiness_ShouldReturnCreatedBusiness() {
        Business business = new Business();
        when(businessRepository.findById(any())).thenReturn(Optional.empty());
        when(businessRepository.save(business)).thenReturn(business);

        Business result = businessService.createBusiness(business);

        assertEquals(business, result);
    }

    @Test
    void createBusiness_WithDuplicateEmail_ShouldThrowDuplicateBusinessException() {
        Business business = new Business();
        when(businessRepository.findById(any())).thenReturn(Optional.of(new Business()));

        assertThrows(DuplicateBusinessException.class, () -> businessService.createBusiness(business));
    }

    @Test
    void updateBusiness_WithValidBusiness_ShouldReturnUpdatedBusiness() {
        Business updatedBusiness = new Business();
        when(businessRepository.save(updatedBusiness)).thenReturn(updatedBusiness);

        Business result = businessService.updateBusiness(updatedBusiness);

        assertEquals(updatedBusiness, result);
    }

    @Test
    void updateBusiness_WithNonExistingId_ShouldThrowBusinessNotFoundException() {
        Business updatedBusiness = new Business();
        when(businessRepository.save(updatedBusiness)).thenThrow(new BusinessNotFoundException(""));

        assertThrows(BusinessNotFoundException.class, () -> businessService.updateBusiness(updatedBusiness));
    }

    @Test
    void deleteBusinessById_WithValidId_ShouldDeleteBusiness() {
        Long businessId = 1L;

        businessService.deleteBusinessById(businessId);

        verify(businessRepository, times(1)).deleteById(businessId);
    }

    @Test
    void deleteBusinessById_WithNonExistingId_ShouldThrowBusinessNotFoundException() {
        Long businessId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(businessRepository).deleteById(businessId);

        assertThrows(BusinessNotFoundException.class, () -> businessService.deleteBusinessById(businessId));
    }

    @Test
    void updateBusiness_WithDataAccessException_ShouldThrowBusinessNotFoundException() {
        Business updatedBusiness = new Business();
        doThrow(new DataAccessException("Simulating DataAccessException") {
        }).when(businessRepository).save(updatedBusiness);

        assertThrows(BusinessNotFoundException.class, () -> businessService.updateBusiness(updatedBusiness));
    }
}
