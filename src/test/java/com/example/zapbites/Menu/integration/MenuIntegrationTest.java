package com.example.zapbites.Menu.integration;

import com.example.zapbites.Menu.Menu;
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
public class MenuIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Test getting all menus")
    void getAll() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/MenuTestFiles/menu_list.json"), List.class); //objectMapper gets a String representation of the .json with TestHelper.readJsonFile() method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/menu")).andExpect(status().isOk()).andReturn(); //mvcResult is the response body of the get call.

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class); // objectMapper gets a String representation of the response body we called earlier.

        Assertions.assertEquals(expectedResult, actualResult); //we compare the json file that we want to get as a result with the response body we queried earlier.
    }

    @Test
    @DisplayName("Test getting menu with id 3")
    void getById() throws Exception {
        var expectedResult = objectMapper.readValue(TestHelper.readJsonFile("/Testfiles/MenuTestFiles/get_by_id_3.json"), Menu.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/menu/3")).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Menu.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("test getting menu with id 99 - does not exist")
    void getByIdNonExistent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu/99")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test updating menu with id 1")
    @DirtiesContext
    void updateById() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/MenuTestFiles/update_Menu_with_id_2_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/MenuTestFiles/update_Menu_with_id_2_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Menu.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/menu/2").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Menu.class);

        Assertions.assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("create a new menu")
    @DirtiesContext
    void createMenu() throws Exception {
        var requestBody = TestHelper.readJsonFile("/Testfiles/MenuTestFiles/created_menu_request.json");
        var responseBody = TestHelper.readJsonFile("/Testfiles/MenuTestFiles/created_menu_response.json");

        var expectedResult = objectMapper.readValue(responseBody, Menu.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/menu").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated()).andReturn();

        var actualResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Menu.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Delete an existing menu")
    @DirtiesContext
    void deleteAnExistingMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/menu/3")).andExpect(status().isNoContent());
    }
}