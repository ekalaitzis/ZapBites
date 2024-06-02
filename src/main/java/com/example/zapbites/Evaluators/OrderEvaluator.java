package com.example.zapbites.Evaluators;

import com.example.zapbites.Order.Order;
import com.example.zapbites.OrderProduct.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEvaluator {

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderEvaluator(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public Order checkForOrderConditions(Order orderToBeChecked) {
        checkForValidBusiness(orderToBeChecked);
        checkForValidCustomer(orderToBeChecked);
        checkForValidCustomerAddress(orderToBeChecked);
        return orderToBeChecked;
    }

    public boolean checkForValidBusiness(Order order) {
        return true;
    }

    public boolean checkForValidCustomer(Order order) {

        if (!order.getCustomerId().getId().equals(order.getCustomerAddressId().getCustomer().getId())) {   //check if the address of the customer is the same with the customer field in order

            throw new IllegalArgumentException("Address must belong to the customer that ordered.");
        }
        return true;

    }

    public boolean checkForValidCustomerAddress(Order order) {

        if (!order.getCustomerAddressId().isPrimary()) {   //check if the address of the order is primary
            throw new IllegalArgumentException("Address must be primary in order to deliver the food that ordered.");
        }
        return true;
    }
}

