package com.example.zapbites.Order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BUSINESS', 'CUSTOMER')") //this is mot secured properly as it is not going to be used in production
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrdersId(#id)")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.getOrderById(id);
        return optionalOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody Order order) {
            Order createdOrder = orderService.createOrder(order);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrders(#order)")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
            Order updatedOrder = orderService.updateOrder(order);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrdersId(#id)")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
