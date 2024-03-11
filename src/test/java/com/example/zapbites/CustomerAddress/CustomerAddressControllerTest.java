package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import com.example.zapbites.CustomerAddress.Exceptions.CustomerAddressNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerAddressControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CustomerAddressService customerAddressService;

    @InjectMocks
    private CustomerAddressController customerAddressController;

    private MockMvc mockMvc;

    private CustomerAddress testCustomerAddress;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(customerAddressController).build();
        testCustomerAddress = new CustomerAddress(1L, "123 Main St", new org.springframework.data.geo.Point(1, 2),true, new Customer());
    }

    @Test
    @DisplayName("Should return a list of all customer addresses")
    public void getAllCustomerAddressesShouldReturnListOfCustomerAddresses() throws Exception {
        List<CustomerAddress> customerAddressList = new ArrayList<>();
        customerAddressList.add(testCustomerAddress);
        customerAddressList.add(testCustomerAddress);
        when(customerAddressService.getAllCustomerAddresses()).thenReturn(customerAddressList);

        mockMvc.perform(get("/customer_address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        verify(customerAddressService, times(1)).getAllCustomerAddresses();
    }

    @Test
    @DisplayName("Should return a customer address when a valid id is given")
    void getCustomerAddressByIdWithValidIdShouldReturnCustomerAddress() throws Exception {
        when(customerAddressService.getCustomerAddressById(testCustomerAddress.getId())).thenReturn(Optional.of(testCustomerAddress));

        mockMvc.perform(get("/customer_address/{id}", testCustomerAddress.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomerAddress.getId()));
        verify(customerAddressService, times(1)).getCustomerAddressById(testCustomerAddress.getId());
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getCustomerAddressByIdWithInvalidIdShouldReturnNotFound() throws Exception {
        Long customerAddressId = 99L;
        when(customerAddressService.getCustomerAddressById(customerAddressId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customer_address/{id}", customerAddressId))
                .andExpect(status().isNotFound());
        verify(customerAddressService, times(1)).getCustomerAddressById(customerAddressId);
    }

    @Test
    @DisplayName("Should create a customer address when valid address attributes are given")
    void createCustomerAddressWithValidAddressShouldReturnCreatedCustomerAddress() throws Exception {
        when(customerAddressService.createCustomerAddress(testCustomerAddress)).thenReturn(testCustomerAddress);

        mockMvc.perform(post("/customer_address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerAddress)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testCustomerAddress.getId()));
        verify(customerAddressService, times(1)).createCustomerAddress(testCustomerAddress);
    }

    @Test
    @DisplayName("Should return an updated customer address when valid updated attributes are given")
    void updateCustomerAddressWithValidAttributesShouldReturnUpdatedCustomerAddress() throws Exception {
        CustomerAddress updatedCustomerAddress = new CustomerAddress(1L, "456 Elm St", new org.springframework.data.geo.Point(3, 4),true, new Customer());

        when(customerAddressService.updateCustomerAddress(any(CustomerAddress.class))).thenReturn(updatedCustomerAddress);

        mockMvc.perform(put("/customer_address/{id}", updatedCustomerAddress.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomerAddress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value(updatedCustomerAddress.getAddress()));
        verify(customerAddressService, times(1)).updateCustomerAddress(updatedCustomerAddress);
    }

    @Test
    @DisplayName("Should return not found when trying to update a non-existing customer address")
    void updateCustomerAddressWithNonExistingIdShouldReturnNotFound() throws Exception {
        Long customerAddressId = 1L;
        CustomerAddress customerAddress = new CustomerAddress(customerAddressId, "123 Main St", new Point(1, 2),true, new Customer());

        when(customerAddressService.updateCustomerAddress(customerAddress)).thenThrow(new CustomerAddressNotFoundException(""));

        mockMvc.perform(put("/customer_address/{id}", customerAddressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerAddress)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return no content when deleting a customer address")
    void deleteCustomerAddressByIdWithValidIdShouldReturnNoContent() throws Exception {
        Long customerAddressId = 1L;

        mockMvc.perform(delete("/customer_address/{id}", customerAddressId))
                .andExpect(status().isNoContent());
        verify(customerAddressService, times(1)).deleteCustomerAddressById(customerAddressId);
    }
}
