package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.BusinessSchedule.Exceptions.BusinessScheduleNotFoundException;
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
        return businessScheduleRepository.save(businessSchedule);
    }

    public BusinessSchedule updateBusinessSchedule(BusinessSchedule updatedBusinessSchedule) {
        List<BusinessSchedule> allBusinessSchedules = getAllBusinessSchedules();

        if (allBusinessSchedules.stream().anyMatch(b -> b.getId().equals(updatedBusinessSchedule.getId()))) {
            return businessScheduleRepository.save(updatedBusinessSchedule);
        } else {
            throw new BusinessScheduleNotFoundException("The business schedule doesn't exist.");
        }
    }


    public void deleteBusinessSchedule(Long id) {
            businessScheduleRepository.deleteById(id);
    }
}
