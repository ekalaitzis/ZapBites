package com.example.zapbites.BusinessSchedule.integration;

import com.example.zapbites.BusinessSchedule.BusinessSchedule;
import com.example.zapbites.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BusinessScheduleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test getting all businesses schedules")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/BusinessScheduleTestFiles/BusinessSchedule_list.json"), List.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/business_schedule").with(user("user").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println(expectedResult);
        System.out.println(actualResult);
    }

    @Test
    @DisplayName("Test getting business schedule with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/BusinessScheduleTestFiles/get_by_id_3.json"), BusinessSchedule.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/business_schedule/3").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BusinessSchedule.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test updating business schedule with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/BusinessScheduleTestFiles/update_BusinessSchedule_with_id_1_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/BusinessScheduleTestFiles/update_BusinessSchedule_with_id_1_response.json");

        var expectedResult = objectMapper.readValue(responseBody, BusinessSchedule.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/business_schedule/1").with(user("flavor@curry.al").roles("BUSINESS")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BusinessSchedule.class);

        Assertions.assertEquals(actualResult, expectedResult);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        Assertions.assertEquals(expectedResult.getWeekday(), actualResult.getWeekday());
        Assertions.assertEquals(expectedResult.getOpeningTime(), actualResult.getOpeningTime());
        Assertions.assertEquals(expectedResult.getClosingTime(), actualResult.getClosingTime());
        Assertions.assertEquals(expectedResult.getBusiness(), actualResult.getBusiness());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getBusiness().getPassword()));

    }

    @Test
    @DisplayName("create a new business schedule")
    @DirtiesContext
    void createBusinessSchedule() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/BusinessScheduleTestFiles/created_businessSchedule_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/BusinessScheduleTestFiles/created_businessSchedule_response.json");

        var expectedResult = objectMapper.readValue(responseBody, BusinessSchedule.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/business_schedule/create").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BusinessSchedule.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getWeekday(), actualResult.getWeekday());
        Assertions.assertEquals(expectedResult.getOpeningTime(), actualResult.getOpeningTime());
        Assertions.assertEquals(expectedResult.getClosingTime(), actualResult.getClosingTime());
        Assertions.assertEquals(expectedResult.getBusiness(), actualResult.getBusiness());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getBusiness().getPassword()));
    }

    @Test
    @DisplayName("Delete an existing business schedule")
    @DirtiesContext
    void deleteAnExistingBusinessSchedule() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/business_schedule/3").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isNoContent());
    }
}