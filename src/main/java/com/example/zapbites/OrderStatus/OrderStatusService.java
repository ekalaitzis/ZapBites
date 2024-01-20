package com.example.zapbites.OrderStatus;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
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

    public OrderStatus updateOrderStatus(OrderStatus orderStatus) {
        try {
            return orderStatusRepository.save(orderStatus);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("The orderStatus with id " + orderStatus.getOrderId() + " not found.", e);
        }
    }

    public void deleteOrderStatusById(Long id) {
        try {
            orderStatusRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("The orderStatus with id " + id + " not found.", e);
        }
    }
}
