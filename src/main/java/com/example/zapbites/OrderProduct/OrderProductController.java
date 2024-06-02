package com.example.zapbites.OrderProduct;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('BUSINESS', 'CUSTOMER')") //this is not secured properly as it is not going to be used in production
    public ResponseEntity<List<OrderProduct>> getAllOrderProducts() {
        List<OrderProduct> orderProducts = orderProductService.getAllOrderProducts();
        return new ResponseEntity<>(orderProducts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrderProductId(#id)")
    public ResponseEntity<OrderProduct> getOrderProductById(@PathVariable Long id) {
        Optional<OrderProduct> optionalOrderProduct = orderProductService.getOrderProductById(id);
        return optionalOrderProduct.map(orderProduct -> new ResponseEntity<>(orderProduct, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrderProduct(@Valid @RequestBody OrderProduct orderProduct) {
            OrderProduct createdOrderProduct = orderProductService.createOrderProduct(orderProduct);
            return new ResponseEntity<>(createdOrderProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrderProduct(#orderProduct)")
    public ResponseEntity<OrderProduct> updateOrderProduct(@RequestBody OrderProduct orderProduct) {
            OrderProduct updatedOrderProduct = orderProductService.updateOrderProduct(orderProduct);
            return new ResponseEntity<>(updatedOrderProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ownerEvaluator.checkForOwnerByOrderProductId(#id)")
    public ResponseEntity<Void> deleteOrderProductById(@PathVariable Long id) {
        orderProductService.deleteOrderProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}