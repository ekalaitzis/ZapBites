package com.example.zapbites.Order;

import com.example.zapbites.Order.Exceptions.DuplicateOrderException;
import com.example.zapbites.Order.Exceptions.OrderNotFoundException;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    private Orders testOrder;

    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        testOrder = new Orders(1L, null, null, null, BigDecimal.valueOf(100.00), new Timestamp(System.currentTimeMillis()));
    }

    @Test
    @DisplayName("Should return a list of all orders")
    public void getAllOrdersShouldReturnListOfOrders() throws Exception {
        List<Orders> orderList = new ArrayList<>();
        orderList.add(new Orders(1L, null, null, null, BigDecimal.valueOf(100.00), new Timestamp(System.currentTimeMillis())));
        orderList.add(new Orders(2L, null, null, null, BigDecimal.valueOf(200.00), new Timestamp(System.currentTimeMillis())));
        when(orderService.getAllOrders()).thenReturn(orderList);

        mockMvc.perform(get("/order")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.length()").value(2));
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    @DisplayName("Should return an order when a valid id is given")
    void getOrderByIdWithValidIdShouldReturnOrder() throws Exception {
        when(orderService.getOrderById(testOrder.getId())).thenReturn(Optional.of(testOrder));

        mockMvc.perform(get("/order/{id}", testOrder.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(testOrder.getId()));
        verify(orderService, times(1)).getOrderById(testOrder.getId());
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getOrderByIdWithInvalidIdShouldReturnNotFound() throws Exception {
        Long orderId = 99L;
        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/order/{id}", orderId)).andExpect(status().isNotFound());

        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    @DisplayName("Should create an order when valid order attributes are given")
    void createOrderWithValidAttributesShouldReturnCreatedOrder() throws Exception {
        when(orderService.createOrder(testOrder)).thenReturn(testOrder);

        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testOrder))).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(testOrder.getId()));
        verify(orderService, times(1)).createOrder(testOrder);
    }

    @Test
    @DisplayName("Should return bad request when an order already exists")
    void createOrder_WithDuplicateOrder_ShouldReturnBadRequest() throws Exception {
        when(orderService.createOrder(testOrder)).thenThrow(new DuplicateOrderException("This Order already exists."));

        mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testOrder))).andExpect(status().isBadRequest()).andExpect(content().string("This Order already exists."));
    }

    @Test
    @DisplayName("Should return an updated order when valid updated attributes are given")
    void updateOrderWithValidAttributesShouldReturnUpdatedOrder() throws Exception {
        Orders updatedOrder = new Orders(1L, null, null, null, BigDecimal.valueOf(150.00), new Timestamp(System.currentTimeMillis()));

        when(orderService.updateOrder(any(Orders.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/order/{id}", updatedOrder.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedOrder))).andExpect(status().isOk()).andExpect(jsonPath("$.totalPrice").value(updatedOrder.getTotalPrice()));
        verify(orderService, times(1)).updateOrder(updatedOrder);
    }

    @Test
    @DisplayName("Should return not found when trying to update a non-existing order")
    void updateOrderWithNonExistingIdShouldReturnNotFound() throws Exception {
        Long orderId = 1L;
        Orders order = new Orders(orderId, null, null, null, BigDecimal.valueOf(100.00), new Timestamp(System.currentTimeMillis()));

        when(orderService.updateOrder(order)).thenThrow(new OrderNotFoundException(""));

        mockMvc.perform(put("/order/{id}", orderId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(order))).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return no content when deleting an order")
    void deleteOrderByIdWithValidIdShouldReturnNoContent() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(delete("/order/{id}", orderId)).andExpect(status().isNoContent());
        verify(orderService, times(1)).deleteOrderById(orderId);
    }
}
