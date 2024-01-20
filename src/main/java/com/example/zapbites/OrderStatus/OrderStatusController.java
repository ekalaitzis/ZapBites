package com.example.zapbites.OrderStatus;

import com.example.zapbites.OrderStatus.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order_status")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }
    @GetMapping
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
        List<OrderStatus> orderStatuses = orderStatusService.getAllOrderStatuses();
        return new ResponseEntity<>(orderStatuses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatus> getOrderStatusById(@PathVariable Long id) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusService.getOrderStatusById(id);
        return optionalOrderStatus.map(orderStatus -> new ResponseEntity<>(orderStatus, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<OrderStatus> createOrderStatus(@RequestBody OrderStatus orderStatus) {
        OrderStatus createdOrderStatus = orderStatusService.createOrderStatus(orderStatus);
        return new ResponseEntity<>(createdOrderStatus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatus> updateOrderStatus(@RequestBody OrderStatus orderStatus) {
        try {
            OrderStatus updatedOrderStatus = orderStatusService.updateOrderStatus(orderStatus);
            return new ResponseEntity<>(updatedOrderStatus, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderStatusById(@PathVariable Long id) {
        orderStatusService.deleteOrderStatusById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
