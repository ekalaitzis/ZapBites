package com.example.zapbites.OrderStatus;

import com.example.zapbites.OrderStatus.Exceptions.DuplicateOrderStatusException;
import com.example.zapbites.OrderStatus.Exceptions.OrderStatusNotFoundException;
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
public class OrderStatusServiceTest {

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusService orderStatusService;

    @Test
    void getAllOrderStatuses_ShouldReturnListOfOrderStatuses() {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        orderStatusList.add(new OrderStatus());
        orderStatusList.add(new OrderStatus());
        when(orderStatusRepository.findAll()).thenReturn(orderStatusList);

        List<OrderStatus> result = orderStatusService.getAllOrderStatuses();

        assertEquals(2, result.size());
    }

    @Test
    void getOrderStatusById_WithValidId_ShouldReturnOrderStatus() {
        Long orderStatusId = 1L;
        OrderStatus orderStatus = new OrderStatus();
        when(orderStatusRepository.findById(orderStatusId)).thenReturn(Optional.of(orderStatus));

        Optional<OrderStatus> result = orderStatusService.getOrderStatusById(orderStatusId);

        assertTrue(result.isPresent());
        assertEquals(orderStatus, result.get());
    }

    @Test
    void getOrderStatusById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long orderStatusId = 1L;
        when(orderStatusRepository.findById(orderStatusId)).thenReturn(Optional.empty());

        Optional<OrderStatus> result = orderStatusService.getOrderStatusById(orderStatusId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createOrderStatus_WithValidOrderStatus_ShouldReturnCreatedOrderStatus() {
        OrderStatus orderStatus = new OrderStatus();
        when(orderStatusRepository.findById(any())).thenReturn(Optional.empty());
        when(orderStatusRepository.save(orderStatus)).thenReturn(orderStatus);

        OrderStatus result = orderStatusService.createOrderStatus(orderStatus);

        assertEquals(orderStatus, result);
    }

    @Test
    void createOrderStatus_WithDuplicateId_ShouldThrowDuplicateOrderStatusException() {
        OrderStatus orderStatus = new OrderStatus();
        when(orderStatusRepository.findById(any())).thenReturn(Optional.of(new OrderStatus()));

        assertThrows(DuplicateOrderStatusException.class, () -> orderStatusService.createOrderStatus(orderStatus));
    }

    @Test
    void updateOrderStatus_WithValidOrderStatus_ShouldReturnUpdatedOrderStatus() {
        OrderStatus updatedOrderStatus = new OrderStatus();
        updatedOrderStatus.setId(1L);
        // Mocking getAllOrderStatuses() to return a list containing the updated orderStatus
        when(orderStatusService.getAllOrderStatuses()).thenReturn(Collections.singletonList(updatedOrderStatus));
        when(orderStatusRepository.save(updatedOrderStatus)).thenReturn(updatedOrderStatus);

        OrderStatus result = orderStatusService.updateOrderStatus(updatedOrderStatus);

        assertEquals(updatedOrderStatus, result);
    }

    @Test
    void updateOrderStatus_WithNonExistingId_ShouldThrowOrderStatusNotFoundException() {
        OrderStatus updatedOrderStatus = new OrderStatus();
        // Mocking getAllOrderStatuses() to return an empty list, simulating that the orderStatus does not exist
        when(orderStatusService.getAllOrderStatuses()).thenReturn(Collections.emptyList());

        assertThrows(OrderStatusNotFoundException.class, () -> orderStatusService.updateOrderStatus(updatedOrderStatus));
    }

    @Test
    void deleteOrderStatusById_WithValidId_ShouldDeleteOrderStatus() {
        Long orderStatusId = 1L;

        orderStatusService.deleteOrderStatusById(orderStatusId);

        verify(orderStatusRepository, times(1)).deleteById(orderStatusId);
    }

    @Test
    void deleteOrderStatusById_WithNonExistingId_ShouldThrowOrderStatusNotFoundException() {
        Long orderStatusId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(orderStatusRepository).deleteById(orderStatusId);

        assertThrows(OrderStatusNotFoundException.class, () -> orderStatusService.deleteOrderStatusById(orderStatusId));
    }
}
