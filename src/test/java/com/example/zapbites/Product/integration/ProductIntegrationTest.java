package com.example.zapbites.Product.integration;

import com.example.zapbites.Product.Product;
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
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all productes")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/ProductTestFiles/product_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product")).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.

        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting product with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/ProductTestFiles/get_by_id_26.json"), Product.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/product/26")).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test getting product with id 99 - does not exist")
    void getByIdNonExistent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/99")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test updating product with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/ProductTestFiles/update_Product_with_id_26_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/ProductTestFiles/update_Product_with_id_26_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Product.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/product/26").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);

        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("create a new product")
    @DirtiesContext
    void createProduct() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/ProductTestFiles/created_product_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/ProductTestFiles/created_product_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Product.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/product").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Delete an existing product")
    @DirtiesContext
    void deleteAnExistingProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/30")).andExpect(status().isNoContent());
    }
}