package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.BusinessSchedule.Exceptions.BusinessScheduleNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusinessScheduleServiceImpl implements BusinessScheduleService{

    private final BusinessScheduleRepository businessScheduleRepository;

    @Autowired
    public BusinessScheduleServiceImpl(BusinessScheduleRepository businessScheduleRepository) {
        this.businessScheduleRepository = businessScheduleRepository;
    }

    @Override
    public List<BusinessSchedule> getAllBusinessSchedules() {
        return businessScheduleRepository.findAll();
    }

    @Override
    public Optional<BusinessSchedule> getBusinessScheduleById(Long id) {
        return businessScheduleRepository.findById(id);
    }

    @Override
    public BusinessSchedule createBusinessSchedule(BusinessSchedule businessSchedule) {
        return businessScheduleRepository.save(businessSchedule);
    }

    @Override
    public BusinessSchedule updateBusinessSchedule(BusinessSchedule updatedBusinessSchedule) {
        List<BusinessSchedule> allBusinessSchedules = getAllBusinessSchedules();

        if (allBusinessSchedules.stream().anyMatch(b -> b.getId().equals(updatedBusinessSchedule.getId()))) {
            return businessScheduleRepository.save(updatedBusinessSchedule);
        } else {
            throw new BusinessScheduleNotFoundException("The business schedule doesn't exist.");
        }
    }

    @Override
    public void deleteBusinessSchedule(Long id) {
        businessScheduleRepository.deleteById(id);
    }
}
