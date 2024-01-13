package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import jakarta.persistence.*;

import java.time.LocalTime;

public class BusinessSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalTime opening;

    @Column(nullable = false)
    private LocalTime closing;

    @OneToOne
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "fk_business_schedule"))
    private Business business;


}
