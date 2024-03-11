package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    public BusinessSchedule() {
    }

    public BusinessSchedule(Long id, WeekdayEnum weekday, LocalTime openingTime, LocalTime closingTime, Business business) {
        this.id = id;
        this.weekday = weekday;
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

    public WeekdayEnum getWeekday() {
        return weekday;
    }

    public void setWeekday(WeekdayEnum weekday) {
        this.weekday = weekday;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessSchedule that = (BusinessSchedule) o;
        return Objects.equals(id, that.id) && weekday == that.weekday && Objects.equals(openingTime, that.openingTime) && Objects.equals(closingTime, that.closingTime) && Objects.equals(business, that.business);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekday, openingTime, closingTime, business);
    }

    @Override
    public String toString() {
        return "BusinessSchedule{" +
                "id=" + id +
                ", weekday=" + weekday +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                ", business=" + business +
                '}';
    }
}
