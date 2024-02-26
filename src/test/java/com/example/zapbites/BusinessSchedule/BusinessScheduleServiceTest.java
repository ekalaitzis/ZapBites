package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import com.example.zapbites.BusinessSchedule.Exceptions.BusinessScheduleNotFoundException;
import com.example.zapbites.BusinessSchedule.Exceptions.DuplicateBusinessScheduleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessScheduleServiceTest {

    @Mock
    private BusinessScheduleRepository businessScheduleRepository;

    @InjectMocks
    private BusinessScheduleService businessScheduleService;

    @Test
    void getAllBusinessSchedules_ShouldReturnListOfBusinessSchedules() {
        List<BusinessSchedule> businessScheduleList = new ArrayList<>();
        businessScheduleList.add(new BusinessSchedule());
        businessScheduleList.add(new BusinessSchedule());
        when(businessScheduleRepository.findAll()).thenReturn(businessScheduleList);

        List<BusinessSchedule> result = businessScheduleService.getAllBusinessSchedules();

        assertEquals(2, result.size());
    }

    @Test
    void getBusinessScheduleById_WithValidId_ShouldReturnBusinessSchedule() {
        Long businessScheduleId = 1L;
        BusinessSchedule businessSchedule = new BusinessSchedule();
        when(businessScheduleRepository.findById(businessScheduleId)).thenReturn(Optional.of(businessSchedule));

        Optional<BusinessSchedule> result = businessScheduleService.getBusinessScheduleById(businessScheduleId);

        assertTrue(result.isPresent());
        assertEquals(businessSchedule, result.get());
    }

    @Test
    void getBusinessScheduleById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long businessScheduleId = 1L;
        when(businessScheduleRepository.findById(businessScheduleId)).thenReturn(Optional.empty());

        Optional<BusinessSchedule> result = businessScheduleService.getBusinessScheduleById(businessScheduleId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createBusinessSchedule_WithValidBusinessSchedule_ShouldReturnCreatedBusinessSchedule() {
        BusinessSchedule businessSchedule = new BusinessSchedule();
        when(businessScheduleRepository.findById(any())).thenReturn(Optional.empty());
        when(businessScheduleRepository.save(businessSchedule)).thenReturn(businessSchedule);

        BusinessSchedule result = businessScheduleService.createBusinessSchedule(businessSchedule);

        assertEquals(businessSchedule, result);
    }

    @Test
    void createBusinessSchedule_WithDuplicateEmail_ShouldThrowDuplicateBusinessScheduleException() {
        BusinessSchedule businessSchedule = new BusinessSchedule();
        when(businessScheduleRepository.findById(any())).thenReturn(Optional.of(new BusinessSchedule()));

        assertThrows(DuplicateBusinessScheduleException.class, () -> businessScheduleService.createBusinessSchedule(businessSchedule));
    }

    @Test
    void updateBusinessSchedule_WithValidBusinessSchedule_ShouldReturnUpdatedBusinessSchedule() {
        BusinessSchedule updatedBusinessSchedule = new BusinessSchedule();
        updatedBusinessSchedule.setId(1L);
        when(businessScheduleService.getAllBusinessSchedules()).thenReturn(Collections.singletonList(updatedBusinessSchedule));
        when(businessScheduleRepository.save(updatedBusinessSchedule)).thenReturn(updatedBusinessSchedule);


        // Act
        BusinessSchedule result = businessScheduleService.updateBusinessSchedule(updatedBusinessSchedule);

        // Assert
        assertEquals(updatedBusinessSchedule, result);
        verify(businessScheduleRepository, times(1)).save(updatedBusinessSchedule);
    }

    @Test
    void updateBusinessSchedule_WithNonExistingId_ShouldThrowBusinessScheduleNotFoundException() {
        // Arrange
        BusinessSchedule updatedBusinessSchedule = new BusinessSchedule();
        updatedBusinessSchedule.setId(1L);

        updatedBusinessSchedule.setOpeningTime(LocalTime.of(9,0));
        updatedBusinessSchedule.setClosingTime(LocalTime.of(18,0));
        updatedBusinessSchedule.setBusiness(new Business(1L, "Test Company", "test@example.com", "password123", "1234567890", "1234567890"));
        when(businessScheduleService.getAllBusinessSchedules()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(BusinessScheduleNotFoundException.class, () -> businessScheduleService.updateBusinessSchedule(updatedBusinessSchedule));
        verify(businessScheduleRepository, times(0)).save(updatedBusinessSchedule);
    }

    @Test
    void deleteBusinessScheduleById_WithValidId_ShouldDeleteBusinessSchedule() {
        Long businessScheduleId = 1L;

        businessScheduleService.deleteBusinessSchedule(businessScheduleId);

        verify(businessScheduleRepository, times(1)).deleteById(businessScheduleId);
    }

    @Test
    void deleteBusinessScheduleById_WithNonExistingId_ShouldThrowBusinessScheduleNotFoundException() {
        Long businessScheduleId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(businessScheduleRepository).deleteById(businessScheduleId);

        assertThrows(BusinessScheduleNotFoundException.class, () -> businessScheduleService.deleteBusinessSchedule(businessScheduleId));
    }

//    @Test
//    void updateBusinessSchedule_WithDataAccessException_ShouldThrowBusinessScheduleNotFoundException() {
//        BusinessSchedule updatedBusinessSchedule = new BusinessSchedule();
//        doThrow(new DataAccessException("Simulating DataAccessException") {
//        }).when(businessScheduleRepository).save(updatedBusinessSchedule);
//
//        assertThrows(BusinessScheduleNotFoundException.class, () -> businessScheduleService.updateBusinessSchedule(updatedBusinessSchedule));
//    }
}
