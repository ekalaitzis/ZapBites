package com.example.zapbites.OrderProduct;

import com.example.zapbites.OrderProduct.Exceptions.DuplicateOrderProductException;
import com.example.zapbites.OrderProduct.Exceptions.OrderProductNotFoundException;
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
public class OrderProductControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private OrderProductService orderProductService;

    @InjectMocks
    private OrderProductController orderProductController;

    private MockMvc mockMvc;

    private OrderProduct testOrderProduct;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(orderProductController).build();
        testOrderProduct = new OrderProduct(1L, null, 2, null);
    }

    @Test
    @DisplayName("Should return a list of all order products")
    public void getAllOrderProductsShouldReturnListOfOrderProducts() throws Exception {
        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(new OrderProduct(1L, null, 2, null));
        orderProductList.add(new OrderProduct(2L, null, 1, null));
        when(orderProductService.getAllOrderProducts()).thenReturn(orderProductList);

        mockMvc.perform(get("/order_product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        verify(orderProductService, times(1)).getAllOrderProducts();
    }

    @Test
    @DisplayName("Should return an order product when a valid id is given")
    void getOrderProductByIdWithValidIdShouldReturnOrderProduct() throws Exception {
        when(orderProductService.getOrderProductById(testOrderProduct.getId())).thenReturn(Optional.of(testOrderProduct));

        mockMvc.perform(get("/order_product/{id}", testOrderProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testOrderProduct.getId()));
        verify(orderProductService, times(1)).getOrderProductById(testOrderProduct.getId());
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getOrderProductByIdWithInvalidIdShouldReturnNotFound() throws Exception {
        Long orderProductId = 99L;
        when(orderProductService.getOrderProductById(orderProductId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/order_product/{id}", orderProductId))
                .andExpect(status().isNotFound());

        verify(orderProductService, times(1)).getOrderProductById(orderProductId);
    }

    @Test
    @DisplayName("Should create an order product when valid order product attributes are given")
    void createOrderProductWithValidAttributesShouldReturnCreatedOrderProduct() throws Exception {
        when(orderProductService.createOrderProduct(testOrderProduct)).thenReturn(testOrderProduct);

        mockMvc.perform(post("/order_product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrderProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testOrderProduct.getId()));
        verify(orderProductService, times(1)).createOrderProduct(testOrderProduct);
    }
    @Test
    @DisplayName("Should return bad request when an order product already exists")
    void createOrderProduct_WithDuplicateOrderProduct_ShouldReturnBadRequest() throws Exception {
        when(orderProductService.createOrderProduct(testOrderProduct)).thenThrow(new DuplicateOrderProductException("OrderProduct already exists"));

        mockMvc.perform(post("/order_product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrderProduct)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("This OrderProduct already exists."));
    }


    @Test
    @DisplayName("Should return an updated order product when valid updated attributes are given")
    void updateOrderProductWithValidAttributesShouldReturnUpdatedOrderProduct() throws Exception {
        OrderProduct updatedOrderProduct = new OrderProduct(1L, null, 3, null);

        when(orderProductService.updateOrderProduct(any(OrderProduct.class))).thenReturn(updatedOrderProduct);

        mockMvc.perform(put("/order_product/{id}", updatedOrderProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrderProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(updatedOrderProduct.getQuantity()));
        verify(orderProductService, times(1)).updateOrderProduct(updatedOrderProduct);
    }

    @Test
    @DisplayName("Should return not found when trying to update a non-existing order product")
    void updateOrderProductWithNonExistingIdShouldReturnNotFound() throws Exception {
        Long orderProductId = 1L;
        OrderProduct orderProduct = new OrderProduct(orderProductId, null, 2, null);

        when(orderProductService.updateOrderProduct(orderProduct)).thenThrow(new OrderProductNotFoundException(""));

        mockMvc.perform(put("/order_product/{id}", orderProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderProduct)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return no content when deleting an order product")
    void deleteOrderProductByIdWithValidIdShouldReturnNoContent() throws Exception {
        Long orderProductId = 1L;

        mockMvc.perform(delete("/order_product/{id}", orderProductId))
                .andExpect(status().isNoContent());
        verify(orderProductService, times(1)).deleteOrderProductById(orderProductId);
    }
}
