package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.BusinessSchedule.Exceptions.BusinessScheduleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BusinessScheduleServiceTest {

    @Mock
    private BusinessScheduleRepository businessScheduleRepository;

    @InjectMocks
    private BusinessScheduleServiceImpl businessScheduleService;

    private BusinessSchedule businessSchedule1, businessSchedule2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        businessSchedule1 = new BusinessSchedule();
        businessSchedule1.setId(1L);

        businessSchedule2 = new BusinessSchedule();
        businessSchedule2.setId(2L);
    }

    @Test
    void getAllBusinessSchedules() {
        List<BusinessSchedule> businessScheduleList = Arrays.asList(businessSchedule1, businessSchedule2);
        when(businessScheduleRepository.findAll()).thenReturn(businessScheduleList);

        List<BusinessSchedule> result = businessScheduleService.getAllBusinessSchedules();

        assertEquals(businessScheduleList, result);
        verify(businessScheduleRepository, times(1)).findAll();
    }

    @Test
    void getBusinessScheduleById() {
        when(businessScheduleRepository.findById(1L)).thenReturn(Optional.of(businessSchedule1));

        Optional<BusinessSchedule> result = businessScheduleService.getBusinessScheduleById(1L);

        assertTrue(result.isPresent());
        assertEquals(businessSchedule1, result.get());
        verify(businessScheduleRepository, times(1)).findById(1L);
    }

    @Test
    void createBusinessSchedule() {
        when(businessScheduleRepository.save(any(BusinessSchedule.class))).thenReturn(businessSchedule1);

        BusinessSchedule result = businessScheduleService.createBusinessSchedule(businessSchedule1);

        assertNotNull(result);
        assertEquals(businessSchedule1, result);
        verify(businessScheduleRepository, times(1)).save(any(BusinessSchedule.class));
    }

    @Test
    void updateBusinessSchedule() {
        when(businessScheduleRepository.findAll()).thenReturn(Arrays.asList(businessSchedule1, businessSchedule2));
        when(businessScheduleRepository.save(any(BusinessSchedule.class))).thenReturn(businessSchedule1);

        BusinessSchedule result = businessScheduleService.updateBusinessSchedule(businessSchedule1);

        assertNotNull(result);
        assertEquals(businessSchedule1, result);
        verify(businessScheduleRepository, times(1)).findAll();
        verify(businessScheduleRepository, times(1)).save(any(BusinessSchedule.class));
    }

    @Test
    void updateBusinessSchedule_BusinessScheduleNotFoundException() {
        BusinessSchedule nonExistingBusinessSchedule = new BusinessSchedule();
        nonExistingBusinessSchedule.setId(3L);
        when(businessScheduleRepository.findAll()).thenReturn(Arrays.asList(businessSchedule1, businessSchedule2));

        assertThrows(BusinessScheduleNotFoundException.class, () -> businessScheduleService.updateBusinessSchedule(nonExistingBusinessSchedule));
        verify(businessScheduleRepository, times(1)).findAll();
        verify(businessScheduleRepository, never()).save(any());
    }

    @Test
    void deleteBusinessSchedule() {
        businessScheduleService.deleteBusinessSchedule(1L);
        verify(businessScheduleRepository, times(1)).deleteById(1L);
    }
}