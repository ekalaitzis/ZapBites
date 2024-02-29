package com.example.zapbites.Customer;

import com.example.zapbites.Customer.Exceptions.CustomerNotFoundException;
import com.example.zapbites.Customer.Exceptions.DuplicateCustomerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class CustomerControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    private Customer testCustomer;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        testCustomer = new Customer(1L, "John", "Doe", "john@example.com", "password123", "1234567890", new ArrayList<>());
    }

    @Test
    @DisplayName("Should return a list of all customers")
    public void getAllCustomersShouldReturnListOfCustomers() throws Exception {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1L, "John", "Doe", "john@example.com", "password123", "1234567890", new ArrayList<>()));
        customerList.add(new Customer(2L, "Jane", "Doe", "jane@example.com", "password456", "9876543210", new ArrayList<>()));
        when(customerService.getAllCustomers()).thenReturn(customerList);

        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("Should return a customer when a valid id is given")
    void getCustomerByIdWithValidIdShouldReturnCustomer() throws Exception {
        when(customerService.getCustomerById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));

        mockMvc.perform(get("/customer/{id}", testCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCustomer.getId()));
        verify(customerService, times(1)).getCustomerById(testCustomer.getId());
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getCustomerByIdWithInvalidIdShouldReturnNotFound() throws Exception {
        Long customerId = 99L;
        when(customerService.getCustomerById(customerId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/customer/{id}", customerId))
                .andExpect(status().isNotFound());
        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    @DisplayName("Should create a customer when valid customer attributes are given")
    void createCustomerWithValidCustomerShouldReturnCreatedCustomer() throws Exception {
        when(customerService.createCustomer(testCustomer)).thenReturn(testCustomer);

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testCustomer.getId()));
        verify(customerService, times(1)).createCustomer(testCustomer);
    }

    @Test
    @DisplayName("Should return bad request when a customer with duplicate email already exists")
    void createCustomerWithDuplicateEmailShouldReturnBadRequest() throws Exception {
        when(customerService.createCustomer(testCustomer)).thenThrow(new DuplicateCustomerException("Customer with this email already exists"));

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("This Customer already exists."));
    }

    @Test
    @DisplayName("Should return an updated customer when valid updated attributes are given")
    void updateCustomerWithValidAttributesShouldReturnUpdatedCustomer() throws Exception {
        Customer updatedCustomer = new Customer(1L, "John", "Doe", "john@example.com", "newpassword", "1234567890", new ArrayList<>());

        when(customerService.updateCustomer(any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(post("/customer/{id}", updatedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value(updatedCustomer.getPassword()));
        verify(customerService, times(1)).updateCustomer(updatedCustomer);
    }

    @Test
    @DisplayName("Should return not found when trying to update a non-existing customer")
    void updateCustomerWithNonExistingIdShouldReturnNotFound() throws Exception {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John", "Doe", "john@example.com", "password123", "1234567890", new ArrayList<>());

        when(customerService.updateCustomer(customer)).thenThrow(new CustomerNotFoundException(""));

        mockMvc.perform(post("/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return no content when deleting a customer")
    void deleteCustomerByIdWithValidIdShouldReturnNoContent() throws Exception {
        Long customerId = 1L;

        mockMvc.perform(delete("/customer/{id}", customerId))
                .andExpect(status().isNoContent());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}
