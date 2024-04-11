package com.example.zapbites.Orders;

import com.example.zapbites.Orders.Exceptions.OrderNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Orders> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Orders createOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public Orders updateOrder(Orders updatedOrder) {
        List<Orders> allOrders = getAllOrders();

        if (allOrders.stream().anyMatch(o -> o.getId().equals(updatedOrder.getId()))) {
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
