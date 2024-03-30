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
public class OrderProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all orderProducts")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/orderProduct_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order_product")).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.

        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting orderProduct with id 11")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/get_by_id_11.json"), OrderProduct.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order_product/11")).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderProduct.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test getting orderProduct with id 99 - does not exist")
    void getByIdNonExistent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/order_product/99")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test updating orderProduct with id 11")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/update_OrderProduct_with_id_11_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/update_OrderProduct_with_id_11_response.json");

        var expectedResult = objectMapper.readValue(responseBody, OrderProduct.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/order_product/1").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderProduct.class);

        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("create a new orderProduct")
    @DirtiesContext
    void createOrderProduct() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/created_orderProduct_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/OrderProductTestFiles/created_orderProduct_response.json");

        var expectedResult = objectMapper.readValue(responseBody, OrderProduct.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/order_product").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderProduct.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Delete an existing orderProduct")
    @DirtiesContext
    void deleteAnExistingOrderProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/order_product/12")).andExpect(status().isNoContent());
    }
}