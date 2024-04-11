package com.example.zapbites.OrderProduct;

import java.util.List;
import java.util.Optional;

public interface OrderProductService {

    List<OrderProduct> getAllOrderProducts();

    Optional<OrderProduct> getOrderProductById(Long id);

    OrderProduct createOrderProduct(OrderProduct orderProduct);

    OrderProduct updateOrderProduct(OrderProduct updatedOrderProduct);

    void deleteOrderProductById(Long id);
}
