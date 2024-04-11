package com.example.zapbites.OrderStatus;

import com.example.zapbites.OrderStatus.Exceptions.OrderStatusNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderStatusServiceImpl implements OrderStatusService{
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }
    @Override
    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }
    @Override
    public Optional<OrderStatus> getOrderStatusById(Long orderStatusId) {
        return orderStatusRepository.findById(orderStatusId);
    }
    @Override
    public OrderStatus createOrderStatus(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }
    @Override
    public OrderStatus updateOrderStatus(OrderStatus updatedOrderStatus) {
        List<OrderStatus> allOrderStatuses = getAllOrderStatuses();

        if (allOrderStatuses.stream().anyMatch(os -> os.getId().equals(updatedOrderStatus.getId()))) {
            return orderStatusRepository.save(updatedOrderStatus);
        } else {
            throw new OrderStatusNotFoundException("OrderStatus not found.");
        }
    }
    @Override
    public void deleteOrderStatusById(Long id) {
            orderStatusRepository.deleteById(id);
    }
}
