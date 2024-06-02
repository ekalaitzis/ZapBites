package com.example.zapbites.Evaluators;

import com.example.zapbites.OrderProduct.OrderProduct;
import com.example.zapbites.OrderProduct.OrderProductRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderProductEvaluator {


    private final OrderProductRepository orderProductRepository;

    public OrderProductEvaluator(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }


    public OrderProduct checkForOrderProductConditions(OrderProduct orderProduct) {
//        checkForValidOrderId(orderProduct);
//        checkForValidQuantity(orderProduct);
//        checkForValidProductId(orderProduct);
        return orderProduct;
    }

    public boolean checkForValidOrderId(OrderProduct orderProduct) {
        return true;
    }


    public boolean checkForValidQuantity(OrderProduct orderProduct) {
        return true;
    }


    public boolean checkForValidProductId(OrderProduct orderProduct) {
        return true;
    }


}
