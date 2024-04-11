package com.example.zapbites.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Orders> getAllOrders();

    Optional<Orders> getOrderById(Long id);

    Orders createOrder(Orders order);

    Orders updateOrder(Orders updatedOrder);

    void deleteOrderById(Long id);
}
