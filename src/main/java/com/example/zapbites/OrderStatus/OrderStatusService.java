package com.example.zapbites.OrderStatus;

import com.example.zapbites.OrderStatus.Exceptions.DuplicateOrderStatusException;
import com.example.zapbites.OrderStatus.Exceptions.OrderStatusNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    public Optional<OrderStatus> getOrderStatusById(Long orderStatusId) {
        return orderStatusRepository.findById(orderStatusId);
    }

    public OrderStatus createOrderStatus(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    public OrderStatus updateOrderStatus(OrderStatus updatedOrderStatus) {
        List<OrderStatus> allOrderStatuses = getAllOrderStatuses();

        if (allOrderStatuses.stream().anyMatch(os -> os.getId().equals(updatedOrderStatus.getId()))) {
            return orderStatusRepository.save(updatedOrderStatus);
        } else {
            throw new OrderStatusNotFoundException("OrderStatus not found.");
        }
    }

    public void deleteOrderStatusById(Long id) {
            orderStatusRepository.deleteById(id);
    }
}
