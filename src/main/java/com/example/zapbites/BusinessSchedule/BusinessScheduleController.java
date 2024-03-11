package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.BusinessSchedule.Exceptions.BusinessScheduleNotFoundException;
import com.example.zapbites.BusinessSchedule.Exceptions.DuplicateBusinessScheduleException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/businessSchedule")
@Validated
public class BusinessScheduleController {

    private final BusinessScheduleService businessScheduleService;

    @Autowired
    public BusinessScheduleController(BusinessScheduleService businessScheduleService) {
        this.businessScheduleService = businessScheduleService;
    }

    @GetMapping
    public ResponseEntity<List<BusinessSchedule>> getAllBusinessSchedules() {
        List<BusinessSchedule> businessSchedules = businessScheduleService.getAllBusinessSchedules();
        return ResponseEntity.status(HttpStatus.OK).body(businessSchedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessSchedule> getBusinessScheduleById(@PathVariable Long id) {
        Optional<BusinessSchedule> optionalBusinessSchedule = businessScheduleService.getBusinessScheduleById(id);
        return optionalBusinessSchedule.map(businessSchedule -> new ResponseEntity<>(businessSchedule, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> createBusinessSchedule(@RequestBody BusinessSchedule businessSchedule) {
        try {
            var createdBusinessSchedule = businessScheduleService.createBusinessSchedule(businessSchedule);
            return new ResponseEntity<>(createdBusinessSchedule, HttpStatus.CREATED);
        } catch (DuplicateBusinessScheduleException e) {    // This may not be needed as a Business can have multiple schedules but use only one as primary.//
            return new ResponseEntity<>("This business already has a few Schedules.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessSchedule> updateBusinessSchedule(@RequestBody BusinessSchedule businessSchedule) {
        try {
            var updatedBusinessSchedule = businessScheduleService.updateBusinessSchedule(businessSchedule);
            return new ResponseEntity<>(updatedBusinessSchedule, HttpStatus.OK);
        } catch (BusinessScheduleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessScheduleById(@PathVariable Long id) {
        businessScheduleService.deleteBusinessSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
