package com.example.zapbites.Order;

import com.example.zapbites.Order.Exceptions.DuplicateOrderException;
import com.example.zapbites.Order.Exceptions.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders_ShouldReturnListOfOrders() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        orderList.add(new Order());
        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
    }

    @Test
    void getOrderById_WithValidId_ShouldReturnOrder() {
        Long orderId = 1L;
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    void getOrderById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(orderId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createOrder_WithValidOrder_ShouldReturnCreatedOrder() {
        Order order = new Order();
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertEquals(order, result);
    }

    @Test
    void createOrder_WithDuplicateId_ShouldThrowDuplicateOrderException() {
        Order order = new Order();
        when(orderRepository.findById(any())).thenReturn(Optional.of(new Order()));

        assertThrows(DuplicateOrderException.class, () -> orderService.createOrder(order));
    }

    @Test
    void updateOrder_WithValidOrder_ShouldReturnUpdatedOrder() {
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        // Mocking getAllOrders() to return a list containing the updated order
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(updatedOrder));
        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(updatedOrder);

        assertEquals(updatedOrder, result);
    }

    @Test
    void updateOrder_WithNonExistingId_ShouldThrowOrderNotFoundException() {
        Order updatedOrder = new Order();
        // Mocking getAllOrders() to return an empty list, simulating that the order does not exist
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(updatedOrder));
    }

    @Test
    void deleteOrderById_WithValidId_ShouldDeleteOrder() {
        Long orderId = 1L;

        orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void deleteOrderById_WithNonExistingId_ShouldThrowOrderNotFoundException() {
        Long orderId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(orderRepository).deleteById(orderId);

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrderById(orderId));
    }
}
