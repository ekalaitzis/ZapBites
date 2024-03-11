package com.example.zapbites.BusinessSchedule;

import com.example.zapbites.Business.Business;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BusinessScheduleTest {

    @Test
    public void testBusinessScheduleEntity() {
        Long expectedId = 1L;
        String expectedCompanyName = "Test Company";
        String expectedEmail = "test@example.com";
        String expectedPassword = "testPassword";
        String expectedTelephone = "1234567890";
        String expectedTaxIdNumber = "ABC123";
        Long expectedScheduleId = 1L;
        WeekdayEnum expectedWeekday = WeekdayEnum.MONDAY;
        LocalTime expectedOpeningTime = LocalTime.of(6, 0);
        LocalTime expectedClosingTime = LocalTime.of(18, 0);
        Business expectedBusiness = new Business(expectedScheduleId, expectedCompanyName, expectedEmail, expectedPassword, expectedTelephone, expectedTaxIdNumber);

        BusinessSchedule businessSchedule = new BusinessSchedule(expectedId,expectedWeekday, expectedOpeningTime, expectedClosingTime, expectedBusiness);

        assertNotNull(businessSchedule);
        assertEquals(expectedScheduleId, businessSchedule.getId());
        assertEquals(expectedOpeningTime, businessSchedule.getOpeningTime());
        assertEquals(expectedClosingTime, businessSchedule.getClosingTime());
        assertEquals(expectedBusiness, businessSchedule.getBusiness());

    }
}

