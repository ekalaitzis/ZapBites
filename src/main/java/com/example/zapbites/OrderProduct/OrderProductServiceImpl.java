package com.example.zapbites.OrderProduct;

import com.example.zapbites.OrderProduct.Exceptions.OrderProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    @Override
    public Optional<OrderProduct> getOrderProductById(Long id) {
        return orderProductRepository.findById(id);
    }

    @Override
    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public OrderProduct updateOrderProduct(OrderProduct updatedOrderProduct) {
        List<OrderProduct> allOrderProducts = getAllOrderProducts();

        if (allOrderProducts.stream().anyMatch(op -> op.getId().equals(updatedOrderProduct.getId()))) {
            return orderProductRepository.save(updatedOrderProduct);
        } else {
            throw new OrderProductNotFoundException("OrderProduct not found.");
        }
    }

    @Override
    public void deleteOrderProductById(Long id) {
        orderProductRepository.deleteById(id);
    }
}