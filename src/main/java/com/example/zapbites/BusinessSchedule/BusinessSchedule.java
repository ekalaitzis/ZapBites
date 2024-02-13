package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "business_schedule")
public class BusinessSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_schedule_seq")
    @SequenceGenerator(name = "business_schedule_seq", sequenceName = "business_schedule_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "opening", nullable = false)
    private LocalTime openingTime;
    @NotBlank
    @Column(name = "closing", nullable = false)
    private LocalTime closingTime;
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "fk_business_schedule"))
    private Business business;

    public BusinessSchedule() {
    }

    public BusinessSchedule(Long id, LocalTime openingTime, LocalTime closingTime, Business business) {
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.business = business;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "BusinessSchedule{" +
                "id=" + id +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                ", business=" + business +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessSchedule that = (BusinessSchedule) o;
        return Objects.equals(id, that.id) && Objects.equals(openingTime, that.openingTime) && Objects.equals(closingTime, that.closingTime) && Objects.equals(business, that.business);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openingTime, closingTime, business);
    }
}
