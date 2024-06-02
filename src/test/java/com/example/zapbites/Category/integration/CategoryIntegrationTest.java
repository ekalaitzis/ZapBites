package com.example.zapbites.Category.integration;

import com.example.zapbites.Category.Category;
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
public class CategoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all categories")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CategoryTestFiles/category_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/category").with(user("user").roles("BUSINESS"))).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.

        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting category with id 20")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/CategoryTestFiles/get_by_id_20.json"), Category.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/category/20").with(user("green@delight.com").roles("BUSINESS"))).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }



    @Test
    @DisplayName("Test updating category with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/CategoryTestFiles/update_Category_with_id_22_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/CategoryTestFiles/update_Category_with_id_22_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Category.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/category/22").with(user("vegan@vibes.com").roles("BUSINESS")).content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);

        Assertions.assertEquals(actualResult, expectedResult);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getName(), actualResult.getName());
        Assertions.assertEquals(expectedResult.getMenu(), actualResult.getMenu());
        Assertions.assertTrue(passwordEncoder.matches("password4", actualResult.getMenu().getBusiness().getPassword()));
    }

    @Test
    @DisplayName("create a new category")
    @DirtiesContext
    void createCategory() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/CategoryTestFiles/created_category_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/CategoryTestFiles/created_category_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Category.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/category/create").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Category.class);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertEquals(expectedResult.getName(), actualResult.getName());
        Assertions.assertEquals(expectedResult.getMenu(), actualResult.getMenu());
        Assertions.assertTrue(passwordEncoder.matches("password1", actualResult.getMenu().getBusiness().getPassword()));
    }

    @Test
    @DisplayName("Delete an existing category")
    @DirtiesContext
    void deleteAnExistingCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/20").with(user("green@delight.com").roles("BUSINESS"))).andExpect(status().isNoContent());
    }
}