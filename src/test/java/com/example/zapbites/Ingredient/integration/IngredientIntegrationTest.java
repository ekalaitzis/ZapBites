package com.example.zapbites.Ingredient.integration;

import com.example.zapbites.Ingredient.Ingredient;
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
public class IngredientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all ingredients")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/IngredientTestFiles/ingredient_list.json"), List.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ingredient").with(user("user").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test getting ingredient with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/IngredientTestFiles/get_by_id_3.json"), Ingredient.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ingredient/3").with(user("sizzle@grill.com").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Ingredient.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Test updating ingredient with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/IngredientTestFiles/update_Ingredient_with_id_1_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/IngredientTestFiles/update_Ingredient_with_id_1_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Ingredient.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/ingredient/1").with(user("green@delight.com").roles("BUSINESS")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Ingredient.class);

        Assertions.assertEquals(actualResult, expectedResult);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getName(), actualResult.getName());
        Assertions.assertEquals(expectedResult.isVegan(), actualResult.isVegan());
        Assertions.assertEquals(expectedResult.isSpicy(), actualResult.isSpicy());
        Assertions.assertEquals(expectedResult.isGlutenFree(), actualResult.isGlutenFree());
        Assertions.assertEquals(expectedResult.getProducts(), actualResult.getProducts());
        Assertions.assertTrue(passwordEncoder.matches("password4", actualResult.getProducts().getFirst().getCategory().getMenu().getBusiness().getPassword()));
    }

    @Test
    @DisplayName("create a new ingredient")
    @DirtiesContext
    void createIngredient() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/IngredientTestFiles/created_ingredient_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/IngredientTestFiles/created_ingredient_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Ingredient.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/ingredient/create").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Ingredient.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getName(), actualResult.getName());
        Assertions.assertEquals(expectedResult.isVegan(), actualResult.isVegan());
        Assertions.assertEquals(expectedResult.isSpicy(), actualResult.isSpicy());
        Assertions.assertEquals(expectedResult.isGlutenFree(), actualResult.isGlutenFree());
        Assertions.assertEquals(expectedResult.getProducts(), actualResult.getProducts());
        Assertions.assertTrue(passwordEncoder.matches("password3", actualResult.getProducts().getFirst().getCategory().getMenu().getBusiness().getPassword()));

    }

    @Test
    @DisplayName("Delete an existing ingredient")
    @DirtiesContext
    void deleteAnExistingIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/ingredient/3").with(user("sizzle@grill.com").roles("BUSINESS"))).andExpect(status().isNoContent());
    }
}