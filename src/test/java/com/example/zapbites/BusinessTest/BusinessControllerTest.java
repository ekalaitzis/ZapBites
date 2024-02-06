package com.example.zapbites.BusinessTest;


import com.example.zapbites.Business.Business;
import com.example.zapbites.Business.BusinessController;
import com.example.zapbites.Business.BusinessService;
import com.example.zapbites.Business.Exceptions.DuplicateBusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BusinessControllerTest {

    @Mock
    private BusinessService businessService;

    @InjectMocks
    private BusinessController businessController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Business testBusiness;

    @BeforeEach
    void init() {
        testBusiness = new Business(1L, "Test Company", "test@example.com", "password123", "1234567890", "1234567890");
    }

    @Test
    void getAllBusinesses_ShouldReturnListOfBusinesses() throws Exception {
        List<Business> businessList = new ArrayList<>();
        businessList.add(new Business());
        businessList.add(new Business());
        when(businessService.getAllBusinesses()).thenReturn(businessList);

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        mockMvc.perform(get("/business")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getBusinessById_WithValidId_ShouldReturnBusiness() throws Exception {
        when(businessService.getBusinessById(testBusiness.getId())).thenReturn(Optional.of(testBusiness));

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        mockMvc.perform(get("/business/{id}", testBusiness.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(testBusiness.getId()));
    }

    @Test
    void getBusinessById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        Long businessId = 99L;
        when(businessService.getBusinessById(businessId)).thenReturn(Optional.empty());

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        mockMvc.perform(get("/business/{id}", businessId)).andExpect(status().isNotFound());
    }

    @Test
    void createBusiness_WithValidBusiness_ShouldReturnCreatedBusiness() throws Exception {
        when(businessService.createBusiness(testBusiness)).thenReturn(testBusiness);

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        String response = mockMvc.perform(post("/business").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testBusiness))).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        System.out.println("Response: " + response);
    }


    @Test
    void createBusiness_WithDuplicateEmail_ShouldReturnBadRequest() throws Exception {
        when(businessService.createBusiness(testBusiness)).thenThrow(new DuplicateBusinessException("Business with this email already exists"));

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        mockMvc.perform(post("/business").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testBusiness))).andExpect(status().isBadRequest()).andExpect(content().string("Business with this email already exists"));
    }


    @Test
    void updateBusiness_WithValidBusiness_ShouldReturnUpdatedBusiness() throws Exception {
        Long businessId = 89L;
        Business originalBusiness = new Business();
        originalBusiness.setId(businessId);

        Business updatedBusiness = new Business();
        updatedBusiness.setId(businessId);
        updatedBusiness.setCompanyName("UpdatedCompany");

        when(businessService.updateBusiness(any(Business.class))).thenReturn(updatedBusiness);

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        mockMvc.perform(put("/business/{id}", businessId).contentType(MediaType.APPLICATION_JSON).content("{\"id\":89, \"companyName\":\"UpdatedCompany\", \"email\":\"updated@example.com\", \"password\":\"updatedPassword\", \"telephone\":\"1234567890\", \"taxIdNumber\":\"12345678901234\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(businessId)).andExpect(jsonPath("$.companyName").value("UpdatedCompany"));
    }


    @Test
    void updateBusiness_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        Long businessId = 1L;
        Business business = new Business();
        business.setId(businessId);

        when(businessService.updateBusiness(business)).thenThrow(new EntityNotFoundException());

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        mockMvc.perform(put("/business/{id}", businessId).contentType(MediaType.APPLICATION_JSON).content("{\"id\":1, \"companyName\":\"UpdatedCompany\", \"email\":\"updated@example.com\", \"password\":\"updatedPassword\", \"telephone\":\"1234567890\", \"taxIdNumber\":\"12345678901234\"}")).andExpect(status().isNotFound());
    }

    @Test
    void deleteBusinessById_WithValidId_ShouldReturnNoContent() throws Exception {
        Long businessId = 1L;

        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
        mockMvc.perform(delete("/business/{id}", businessId)).andExpect(status().isNoContent());

        verify(businessService, times(1)).deleteBusinessById(businessId);
    }
}

