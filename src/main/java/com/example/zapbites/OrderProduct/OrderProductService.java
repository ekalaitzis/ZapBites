package com.example.zapbites.OrderProduct;

import com.example.zapbites.OrderProduct.Exceptions.DuplicateOrderProductException;
import com.example.zapbites.OrderProduct.Exceptions.OrderProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    public Optional<OrderProduct> getOrderProductById(Long id) {
        return orderProductRepository.findById(id);
    }

    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        if (orderProductRepository.findById(orderProduct.getId()).isPresent()) {
            throw new DuplicateOrderProductException("OrderProduct  " + orderProduct + " already exists");
        }
        return orderProductRepository.save(orderProduct);
    }

    public OrderProduct updateOrderProduct(OrderProduct orderProduct) {
        try {
            return orderProductRepository.save(orderProduct);
        } catch (DataAccessException e) {
            throw new OrderProductNotFoundException("The orderProduct with id " + orderProduct.getOrderId() + " not found.", e);
        }

    }

    public void deleteOrderProductById(Long id) {
        try {
            orderProductRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new OrderProductNotFoundException("The orderProduct with id " + id + " not found.", e);
        }
    }
}