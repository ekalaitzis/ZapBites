package com.example.zapbites.BusinessSchedule;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/businessSchedule")
public class BusinessScheduleController {

    private final BusinessScheduleService businessScheduleService;

    @Autowired
    public BusinessScheduleController(BusinessScheduleService businessScheduleService) {
        this.businessScheduleService = businessScheduleService;
    }

    @GetMapping
    public ResponseEntity<List<BusinessSchedule>> getAllBusinessSchedules() {
        List<BusinessSchedule> businessSchedules = businessScheduleService.getAllBusinessSchedulles();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(businessSchedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessSchedule> getBusinessScheduleById(@PathVariable Long id) {
        Optional<BusinessSchedule> optionalBusinessSchedule = businessScheduleService.getBusinessScheduleById(id);
        return optionalBusinessSchedule
                .map(businessSchedule -> new ResponseEntity<>(businessSchedule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BusinessSchedule> createBusinessSchedule(@RequestBody BusinessSchedule businessSchedule) {
        var createdBusinessSchedule = businessScheduleService.createBusinessSchedule(businessSchedule);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createdBusinessSchedule);
    }

    @PostMapping("/{id}")
    public ResponseEntity<BusinessSchedule> updateBusinessSchedule(@RequestBody BusinessSchedule businessSchedule) {
        try {
            var updatedBusinessSchedule = businessScheduleService.updateBusinessSchedule(businessSchedule);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedBusinessSchedule);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessScheduleById(Long id) {
        businessScheduleService.deleteBusinessSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
