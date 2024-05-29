package com.example.zapbites.Business.integration;

import com.example.zapbites.Business.Business;
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
import org.springframework.security.test.context.support.WithMockUser;
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
public class BusinessIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all businesses")
    @WithMockUser(roles = "BUSINESS")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/BusinessTestFiles/business_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/business").with(user("user").roles("BUSINESS"))).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.
        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting business with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/BusinessTestFiles/get_by_id_3.json"), Business.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/business/3").with(user("green@delight.com").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Business.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test updating business with id 1")
    @DirtiesContext
    @WithMockUser(username = "flavor@curry.al",password = "$2a$10$UxvZ/kvLYMCqtUthk3Wznea7JqX.ejUaJ9UZBIgUWSYnHF7WhVJ/6",roles = "BUSINESS") //even if the requestBody / responseBody username / password is changed the Business is still updated and I don't get bad credentials
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/BusinessTestFiles/update_Business_with_id_1_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/BusinessTestFiles/update_Business_with_id_1_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Business.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/business/1").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE).with(user("flavor@curry.al").password("$2a$10$UxvZ/kvLYMCqtUthk3Wznea7JqX.ejUaJ9UZBIgUWSYnHF7WhVJ/6").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Business.class);

        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("create a new business")
    @DirtiesContext
    void createBusiness() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/BusinessTestFiles/created_business_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/BusinessTestFiles/created_business_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Business.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/business/register").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Business.class);

        // Create a BCryptPasswordEncoder instance
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Compare fields except the password
        Assertions.assertEquals(expectedResult.getCompanyName(), actualResult.getCompanyName());
        Assertions.assertEquals(expectedResult.getEmail(), actualResult.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("passwordpiazza", actualResult.getPassword()));
        Assertions.assertEquals(expectedResult.getTelephone(), actualResult.getTelephone());
        Assertions.assertEquals(expectedResult.getTaxIdNumber(), actualResult.getTaxIdNumber());
        Assertions.assertEquals(expectedResult.getRole(), actualResult.getRole());
    }

    @Test
    @DisplayName("Delete an existing business")
    @DirtiesContext
    void deleteAnExistingBusiness() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/business/1").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isNoContent());
    }
}