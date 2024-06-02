package com.example.zapbites.BusinessSchedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/business_schedule")
@Validated
public class BusinessScheduleController {

    private final BusinessScheduleService businessScheduleService;

    @Autowired
    public BusinessScheduleController(BusinessScheduleService businessScheduleService) {
        this.businessScheduleService = businessScheduleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BUSINESS')") //this is not secured properly as it is not going to be used in production
    public ResponseEntity<List<BusinessSchedule>> getAllBusinessSchedules() {
        List<BusinessSchedule> businessSchedules = businessScheduleService.getAllBusinessSchedules();
        return ResponseEntity.status(HttpStatus.OK).body(businessSchedules);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByBusinessScheduleId(#id)")
    public ResponseEntity<BusinessSchedule> getBusinessScheduleById(@PathVariable Long id) {
        Optional<BusinessSchedule> optionalBusinessSchedule = businessScheduleService.getBusinessScheduleById(id);
        return optionalBusinessSchedule.map(businessSchedule -> new ResponseEntity<>(businessSchedule, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createBusinessSchedule(@RequestBody BusinessSchedule businessSchedule) {
            var createdBusinessSchedule = businessScheduleService.createBusinessSchedule(businessSchedule);
            return new ResponseEntity<>(createdBusinessSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByBusinessSchedule(#businessSchedule)")
    public ResponseEntity<BusinessSchedule> updateBusinessSchedule(@RequestBody BusinessSchedule businessSchedule) {
            var updatedBusinessSchedule = businessScheduleService.updateBusinessSchedule(businessSchedule);
            return new ResponseEntity<>(updatedBusinessSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByBusinessScheduleId(#id)")
    public ResponseEntity<Void> deleteBusinessScheduleById(@PathVariable Long id) {
        businessScheduleService.deleteBusinessSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
