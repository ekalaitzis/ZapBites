package com.example.zapbites.Order;

import com.example.zapbites.Evaluators.OrderEvaluator;
import com.example.zapbites.Order.Exceptions.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEvaluator orderEvaluator;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1, order2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order1 = new Order();
        order1.setId(1L);

        order2 = new Order();
        order2.setId(2L);
    }

    @Test
    void getAllOrders() {
        List<Order> orderList = Arrays.asList(order1, order2);
        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> result = orderService.getAllOrders();

        assertEquals(orderList, result);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(order1, result.get());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void createOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        Order result = orderService.createOrder(order1);

        assertNotNull(result);
        assertEquals(order1, result);
        verify(orderEvaluator, times(1)).checkForOrderConditions(order1);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        Order result = orderService.updateOrder(order1);

        assertNotNull(result);
        assertEquals(order1, result);
        verify(orderEvaluator, times(1)).checkForOrderConditions(order1);
        verify(orderRepository, times(1)).findAll();
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_OrderNotFoundException() {
        Order nonExistingOrder = new Order();
        nonExistingOrder.setId(3L);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(nonExistingOrder));
        verify(orderEvaluator, never()).checkForOrderConditions(any());
        verify(orderRepository, times(1)).findAll();
        verify(orderRepository, never()).save(any());
    }

    @Test
    void deleteOrderById() {
        orderService.deleteOrderById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}