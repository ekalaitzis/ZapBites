package com.example.zapbites.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long id);

    Order createOrder(Order order);

    Order updateOrder(Order updatedOrder);

    void deleteOrderById(Long id);
}
