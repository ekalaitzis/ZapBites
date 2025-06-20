package com.example.zapbites.Order.integration;

import com.example.zapbites.Order.Order;
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
public class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all orders")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrdersTestFiles/orders_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders").with(user("crispy@bites.com").roles("BUSINESS"))).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.

        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting orders with id 6")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrdersTestFiles/get_by_id_6.json"), Order.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/6").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Order.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test getting orders with id 6")
    void getByIdAsCustomer() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrdersTestFiles/get_by_id_6.json"), Order.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/6").with(user("emily.martinez@email.com").roles("CUSTOMER"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Order.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test updating orders with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrdersTestFiles/update_Orders_with_id_6_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrdersTestFiles/update_Orders_with_id_6_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Order.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/orders/6").with(user("flavor@curry.al").roles("BUSINESS")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Order.class);

        Assertions.assertEquals(actualResult, expectedResult);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getBusinessId(), actualResult.getBusinessId());
        Assertions.assertEquals(expectedResult.getCustomerId(), actualResult.getCustomerId());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getBusinessId().getPassword()));
        Assertions.assertTrue(passwordEncoder.matches("password6", actualResult.getCustomerId().getPassword()));
        Assertions.assertEquals(expectedResult.getCustomerAddressId(), actualResult.getCustomerAddressId());
        Assertions.assertEquals(expectedResult.getTotalPrice(), actualResult.getTotalPrice());
        Assertions.assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
    }

    @Test
    @DisplayName("create a new orders")
    @DirtiesContext
    void createOrders() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrdersTestFiles/created_orders_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrdersTestFiles/created_orders_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Order.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/orders/create").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Order.class);


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getBusinessId(), actualResult.getBusinessId());
        Assertions.assertEquals(expectedResult.getCustomerId(), actualResult.getCustomerId());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getBusinessId().getPassword()));
        Assertions.assertTrue(passwordEncoder.matches("password6", actualResult.getCustomerId().getPassword()));
        Assertions.assertEquals(expectedResult.getCustomerAddressId(), actualResult.getCustomerAddressId());
        Assertions.assertEquals(expectedResult.getTotalPrice(), actualResult.getTotalPrice());
        Assertions.assertEquals(expectedResult.getCreatedAt(), actualResult.getCreatedAt());
    }

    @Test
    @DisplayName("Delete an existing orders")
    @DirtiesContext
    void deleteAnExistingOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/6").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isNoContent());
    }
}