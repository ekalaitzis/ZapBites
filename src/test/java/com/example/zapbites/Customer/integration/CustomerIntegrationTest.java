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
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all customers")
    @WithMockUser(roles = "CUSTOMER")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/customer_list.json"), List.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer").with(user("user").roles("CUSTOMER"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test getting customer with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/get_by_id_3.json"), Customer.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer/3").with(user("bob.johnson@email.com").roles("CUSTOMER"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }


    @Test
    @DisplayName("Test updating customer with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/update_Customer_with_id_1_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/CustomerTestFiles/update_Customer_with_id_1_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Customer.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/customer/1").with(user("john.doe@email.com").roles("CUSTOMER")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

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

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customer/register").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getFirstName(), actualResult.getFirstName());
        Assertions.assertEquals(expectedResult.getLastName(), actualResult.getLastName());
        Assertions.assertEquals(expectedResult.getEmail(), actualResult.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getPassword()));
        Assertions.assertEquals(expectedResult.getTelephone(), actualResult.getTelephone());
        Assertions.assertEquals(expectedResult.getRole(), actualResult.getRole());
    }

    @Test
    @DisplayName("Delete an existing customer")
    @DirtiesContext
    void deleteAnExistingCustomer() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.delete("/customer/3").with(user("bob.johnson@email.com").roles("CUSTOMER"))).andExpect(status().isNoContent());
    }
}