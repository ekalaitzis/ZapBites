package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusinessScheduleService {

    private final BusinessScheduleRepository businessScheduleRepository;

    @Autowired
    public BusinessScheduleService(BusinessScheduleRepository businessScheduleRepository) {
        this.businessScheduleRepository = businessScheduleRepository;
    }

    public List<BusinessSchedule> getAllBusinessSchedules() {
        return businessScheduleRepository.findAll();
    }

    public Optional<BusinessSchedule> getBusinessScheduleById(Long id) {
        return businessScheduleRepository.findById(id);
    }

    public BusinessSchedule createBusinessSchedule(BusinessSchedule businessSchedule) {
        if (businessScheduleRepository.findById(businessSchedule.getId()).isPresent()) {
            throw new DuplicateBusinessException("This business already has a Schedule. You can update the existing one.");
        }
        return businessScheduleRepository.save(businessSchedule);
    }

    public BusinessSchedule updateBusinessSchedule(BusinessSchedule updatedBusinessSchedule) {
        try {
            return businessScheduleRepository.save(updatedBusinessSchedule);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("BusinessSchedule with id " + updatedBusinessSchedule.getId() + "not found");
            // in the future the exception message might be replaced with "BusinessSchedule  for " + business + 'not found"
        }
    }

    public void deleteBusinessSchedule(Long id) {
        try {
            businessScheduleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("BusinessSchedule with id " + id + "not found");
        }
    }
}
