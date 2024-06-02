package com.example.zapbites.OrderStatus.integration;

import com.example.zapbites.OrderStatus.OrderStatus;
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
public class OrderStatusIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all order status")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrderStatusTestFiles/orderStatus_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order_status").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.

        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting order status with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrderStatusTestFiles/get_by_id_29.json"), OrderStatus.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order_status/29").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderStatus.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }


    @Test
    @DisplayName("Test updating orderStatus with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrderStatusTestFiles/update_OrderStatus_with_id_29_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrderStatusTestFiles/update_OrderStatus_with_id_29_response.json");

        var expectedResult = objectMapper.readValue(responseBody, OrderStatus.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/order_status/29").with(user("flavor@curry.al").roles("BUSINESS")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderStatus.class);

        Assertions.assertEquals(actualResult, expectedResult);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getOrderId(), actualResult.getOrderId());
        Assertions.assertEquals(expectedResult.getOrderStatusEnum(), actualResult.getOrderStatusEnum());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getOrderId().getBusinessId().getPassword()));
        Assertions.assertEquals(expectedResult.getStatusChangedAt(), actualResult.getStatusChangedAt());
    }

    @Test
    @DisplayName("create a new orderStatus")
    @DirtiesContext
    void createOrderStatus() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrderStatusTestFiles/created_orderStatus_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrderStatusTestFiles/created_orderStatus_response.json");

        var expectedResult = objectMapper.readValue(responseBody, OrderStatus.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/order_status/create").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderStatus.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getOrderId(), actualResult.getOrderId());
        Assertions.assertEquals(expectedResult.getOrderStatusEnum(), actualResult.getOrderStatusEnum());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getOrderId().getBusinessId().getPassword()));
        Assertions.assertEquals(expectedResult.getStatusChangedAt(), actualResult.getStatusChangedAt());
    }

    @Test
    @DisplayName("Delete an existing orderStatus")
    @DirtiesContext
    void deleteAnExistingOrderStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/order_status/29").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isNoContent());
    }
}