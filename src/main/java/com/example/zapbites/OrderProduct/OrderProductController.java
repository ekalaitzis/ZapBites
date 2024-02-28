package com.example.zapbites.OrderProduct;

import com.example.zapbites.OrderProduct.Exceptions.DuplicateOrderProductException;
import com.example.zapbites.OrderProduct.Exceptions.OrderProductNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order_product")
public class OrderProductController {
    private final OrderProductService orderProductService;

    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @GetMapping
    public ResponseEntity<List<OrderProduct>> getAllOrderProducts() {
        List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
        return new ResponseEntity<>(orderProducts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> getOrderProductById(@PathVariable Long id) {
        Optional<OrderProduct> optionalOrderProduct = orderProductService.getOrderProductById(id);
        return optionalOrderProduct.map(orderProduct -> new ResponseEntity<>(orderProduct, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Object> createOrderProduct(@Valid @RequestBody OrderProduct orderProduct) {
        try {
            OrderProduct createdOrderProduct = orderProductService.createOrderProduct(orderProduct);
            return new ResponseEntity<>(createdOrderProduct, HttpStatus.CREATED);
        } catch (DuplicateOrderProductException e) {
            return new ResponseEntity<>("This OrderProduct already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderProduct> updateOrderProduct(@RequestBody OrderProduct orderProduct) {
        try {
            OrderProduct updatedOrderProduct = orderProductService.updateOrderProduct(orderProduct);
            return new ResponseEntity<>(updatedOrderProduct, HttpStatus.OK);
        } catch (OrderProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderProductById(@PathVariable Long id) {
        orderProductService.deleteOrderProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
