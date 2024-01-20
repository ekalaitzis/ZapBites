package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessScheduleRepository extends JpaRepository<BusinessSchedule, Long> {
}
