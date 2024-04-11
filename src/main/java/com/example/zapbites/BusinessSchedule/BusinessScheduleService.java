package com.example.zapbites.BusinessSchedule;

import java.util.List;
import java.util.Optional;

public interface BusinessScheduleService {
    List<BusinessSchedule> getAllBusinessSchedules();

    Optional<BusinessSchedule> getBusinessScheduleById(Long id);


    BusinessSchedule createBusinessSchedule(BusinessSchedule businessSchedule);

    BusinessSchedule updateBusinessSchedule(BusinessSchedule updatedBusinessSchedule);

    void deleteBusinessSchedule(Long id);











}
