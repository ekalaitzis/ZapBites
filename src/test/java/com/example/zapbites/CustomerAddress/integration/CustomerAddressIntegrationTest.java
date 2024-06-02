package com.example.zapbites.CustomerAddress.integration;

import com.example.zapbites.CustomerAddress.CustomerAddress;
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
public class CustomerAddressIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all customerAddresses")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CustomerAddressTestFiles/customerAddress_list.json"), List.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer_address").with(user("user").roles("CUSTOMER"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test getting customerAddress with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CustomerAddressTestFiles/get_by_id_5.json"), CustomerAddress.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customer_address/5").with(user("alice.johnson@email.com").roles("CUSTOMER"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerAddress.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }



    @Test
    @DisplayName("Test updating customerAddress with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/CustomerAddressTestFiles/update_CustomerAddress_with_id_1_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/CustomerAddressTestFiles/update_CustomerAddress_with_id_1_response.json");

        var expectedResult = objectMapper.readValue(responseBody, CustomerAddress.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/customer_address/1").with(user("alice.johnson@email.com").roles("CUSTOMER")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerAddress.class);

        Assertions.assertEquals(actualResult, expectedResult);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getAddress(), actualResult.getAddress());
        Assertions.assertEquals(expectedResult.getGeolocation(), actualResult.getGeolocation());
        Assertions.assertEquals(expectedResult.isPrimary(), actualResult.isPrimary());
        Assertions.assertTrue(passwordEncoder.matches("password4", actualResult.getCustomer().getPassword()));
        Assertions.assertEquals(expectedResult.getCustomer(), actualResult.getCustomer());

    }

    @Test
    @DisplayName("create a new customerAddress")
    @DirtiesContext
    void createCustomerAddress() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/CustomerAddressTestFiles/created_customerAddress_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/CustomerAddressTestFiles/created_customerAddress_response.json");

        var expectedResult = objectMapper.readValue(responseBody, CustomerAddress.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/customer_address/create").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerAddress.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getAddress(), actualResult.getAddress());
        Assertions.assertEquals(expectedResult.getGeolocation(), actualResult.getGeolocation());
        Assertions.assertEquals(expectedResult.isPrimary(), actualResult.isPrimary());
        Assertions.assertTrue(passwordEncoder.matches("password4", actualResult.getCustomer().getPassword()));
        Assertions.assertEquals(expectedResult.getCustomer(), actualResult.getCustomer());
    }

    @Test
    @DisplayName("Delete an existing customerAddress")
    @DirtiesContext
    void deleteAnExistingCustomerAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customer_address/5").with(user("alice.johnson@email.com").roles("CUSTOMER"))).andExpect(status().isNoContent());
    }
}