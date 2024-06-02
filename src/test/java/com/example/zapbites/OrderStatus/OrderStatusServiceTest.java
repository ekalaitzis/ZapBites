package com.example.zapbites.OrderStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderStatusServiceTest {

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusServiceImpl orderStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrderStatuses() {
        OrderStatus orderStatus1 = new OrderStatus();
        OrderStatus orderStatus2 = new OrderStatus();
        List<OrderStatus> orderStatusList = Arrays.asList(orderStatus1, orderStatus2);
        when(orderStatusRepository.findAll()).thenReturn(orderStatusList);

        List<OrderStatus> result = orderStatusService.getAllOrderStatuses();

        assertEquals(orderStatusList, result);
    }

    @Test
    void getOrderStatusById() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1L);
        when(orderStatusRepository.findById(1L)).thenReturn(Optional.of(orderStatus));

        Optional<OrderStatus> result = orderStatusService.getOrderStatusById(1L);

        assertEquals(orderStatus, result.get());
    }

    @Test
    void createOrderStatus() {
        OrderStatus orderStatus = new OrderStatus();
        when(orderStatusRepository.save(orderStatus)).thenReturn(orderStatus);

        OrderStatus result = orderStatusService.createOrderStatus(orderStatus);

        assertEquals(orderStatus, result);
    }

    @Test
    void updateOrderStatus() {
        OrderStatus orderStatus1 = new OrderStatus();
        orderStatus1.setId(1L);
        OrderStatus orderStatus2 = new OrderStatus();
        orderStatus2.setId(2L);
        List<OrderStatus> orderStatusList = Arrays.asList(orderStatus1, orderStatus2);
        when(orderStatusRepository.findAll()).thenReturn(orderStatusList);
        when(orderStatusRepository.save(orderStatus1)).thenReturn(orderStatus1);

        OrderStatus result = orderStatusService.updateOrderStatus(orderStatus1);

        assertEquals(orderStatus1, result);
    }


    @Test
    void deleteOrderStatusById() {
        orderStatusService.deleteOrderStatusById(1L);

        verify(orderStatusRepository, times(1)).deleteById(1L);
    }
}