package com.example.zapbites.OrderStatus;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order_status")
@Validated
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BUSINESS', 'CUSTOMER')") //this is mot secured properly as its not going to be used in production
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
        List<OrderStatus> orderStatuses = orderStatusService.getAllOrderStatuses();
        return new ResponseEntity<>(orderStatuses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrderStatusId(#id)")
    public ResponseEntity<OrderStatus> getOrderStatusById(@PathVariable Long id) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusService.getOrderStatusById(id);
        return optionalOrderStatus.map(orderStatus -> new ResponseEntity<>(orderStatus, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrderStatus(@Valid @RequestBody OrderStatus orderStatus) {
            OrderStatus createdOrderStatus = orderStatusService.createOrderStatus(orderStatus);
            return new ResponseEntity<>(createdOrderStatus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrderStatus(#orderStatus)")
    public ResponseEntity<OrderStatus> updateOrderStatus(@RequestBody OrderStatus orderStatus) {
            OrderStatus updatedOrderStatus = orderStatusService.updateOrderStatus(orderStatus);
            return new ResponseEntity<>(updatedOrderStatus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrderStatusId(#id)")
    public ResponseEntity<Void> deleteOrderStatusById(@PathVariable Long id) {
        orderStatusService.deleteOrderStatusById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
