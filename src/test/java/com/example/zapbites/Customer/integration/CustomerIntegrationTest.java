package com.example.zapbites.Customer.integration;

import com.example.zapbites.Customer.Customer;
import com.example.zapbites.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all customers")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/customer_list.json"), List.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer")).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test getting customer with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/get_by_id_3.json"), Customer.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer/3")).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test getting customer with id 99 - does not exist")
    void getByIdNonExistent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/99")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test updating customer with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/update_Customer_with_id_1_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/update_Customer_with_id_1_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Customer.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/customer/1").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);

        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("create a new customer")
    @DirtiesContext
    void createCustomer() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/created_customer_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/created_customer_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Customer.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customer").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Delete an existing customer")
    @DirtiesContext
    void deleteAnExistingCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/3")).andExpect(status().isNoContent());
    }
}