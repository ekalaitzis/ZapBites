package com.example.zapbites.BusinessSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessScheduleRepository extends JpaRepository<BusinessSchedule, Long> {
}
