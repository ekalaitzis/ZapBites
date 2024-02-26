package com.example.zapbites.Order;

import com.example.zapbites.Order.Exceptions.DuplicateOrderException;
import com.example.zapbites.Order.Exceptions.OrderNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        if (orderRepository.findById(order.getId()).isPresent()) {
            throw new DuplicateOrderException("Order with Id " + order.getId() + " already exists");
        }
        return orderRepository.save(order);
    }

    public Order updateOrder(Order updatedOrder) {
        List<Order> allOrders = getAllOrders();

        if (allOrders.stream().anyMatch(o -> o.getId().equals(updatedOrder.getId()))) {
            return orderRepository.save(updatedOrder);
        } else {
            throw new OrderNotFoundException("Order with id " + updatedOrder.getId() + " not found.");
        }
    }

    public void deleteOrderById(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new OrderNotFoundException("The order with id " + id + " not found.", e);
        }
    }
}
