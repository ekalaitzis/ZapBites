package com.example.zapbites.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderStatusService {
    List<OrderStatus> getAllOrderStatuses();

    Optional<OrderStatus> getOrderStatusById(Long orderStatusId);

    OrderStatus createOrderStatus(OrderStatus orderStatus);

    OrderStatus updateOrderStatus(OrderStatus updatedOrderStatus);

    void deleteOrderStatusById(Long id);

}
