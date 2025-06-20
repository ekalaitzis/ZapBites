package com.example.zapbites.OrderProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrderId(OrderProduct orderProduct);
}
