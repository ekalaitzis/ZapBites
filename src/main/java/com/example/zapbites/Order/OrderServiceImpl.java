package com.example.zapbites.Order;

import com.example.zapbites.Evaluators.OrderEvaluator;
import com.example.zapbites.Order.Exceptions.OrderNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEvaluator orderEvaluator;

    public OrderServiceImpl(OrderRepository orderRepository, OrderEvaluator orderEvaluator) {
        this.orderRepository = orderRepository;
        this.orderEvaluator = orderEvaluator;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(Order order) {
        orderEvaluator.checkForOrderConditions(order);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order updatedOrder) {
        List<Order> allOrders = getAllOrders();

        if (allOrders.stream().anyMatch(o -> o.getId().equals(updatedOrder.getId()))) {
            orderEvaluator.checkForOrderConditions(updatedOrder);
            return orderRepository.save(updatedOrder);
        } else {
            throw new OrderNotFoundException("Order not found.");
        }
    }

    @Override
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
