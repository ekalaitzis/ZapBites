package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "business_schedule")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BusinessSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_schedule_seq")
    @SequenceGenerator(name = "business_schedule_seq", sequenceName = "business_schedule_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "weekday", nullable = false)
    @Enumerated(EnumType.STRING)
    private WeekdayEnum  weekday;
    @Column(name = "opening")
    private LocalTime openingTime;
    @Column(name = "closing")
    private LocalTime closingTime;
    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "fk_business_schedule"))
    private Business business;


}
