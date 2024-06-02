package com.example.zapbites.OrderProduct.integration;

import com.example.zapbites.OrderProduct.OrderProduct;
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
public class OrderProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all orderProducts")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/orderProduct_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order_product").with(user("crispy@bites.com").roles("BUSINESS"))).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.

        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting orderProduct with id 11")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/get_by_id_11.json"), OrderProduct.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order_product/11").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderProduct.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test getting orderProduct with id 11")
    void getByIdByCustomer() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/get_by_id_11.json"), OrderProduct.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order_product/11").with(user("emily.martinez@email.com").roles("CUSTOMER"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderProduct.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }


    @Test
    @DisplayName("Test updating orderProduct with id 11")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/update_OrderProduct_with_id_11_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/update_OrderProduct_with_id_11_response.json");

        var expectedResult = objectMapper.readValue(responseBody, OrderProduct.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/order_product/1").with(user("flavor@curry.al").roles("BUSINESS")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderProduct.class);

        Assertions.assertEquals(actualResult, expectedResult);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getOrderId(), actualResult.getOrderId());
        Assertions.assertEquals(expectedResult.getQuantity(), actualResult.getQuantity());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getOrderId().getBusinessId().getPassword()));
        Assertions.assertEquals(expectedResult.getProduct_id(), actualResult.getProduct_id());
    }

    @Test
    @DisplayName("create a new orderProduct")
    @DirtiesContext
    void createOrderProduct() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/created_orderProduct_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/created_orderProduct_response.json");

        var expectedResult = objectMapper.readValue(responseBody, OrderProduct.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/order_product/create").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderProduct.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getOrderId(), actualResult.getOrderId());
        Assertions.assertEquals(expectedResult.getQuantity(), actualResult.getQuantity());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getOrderId().getBusinessId().getPassword()));
        Assertions.assertEquals(expectedResult.getProduct_id(), actualResult.getProduct_id());
    }

    @Test
    @DisplayName("Delete an existing orderProduct")
    @DirtiesContext
    void deleteAnExistingOrderProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/order_product/11").with(user("flavor@curry.al").roles("BUSINESS"))).andExpect(status().isNoContent());
    }
}