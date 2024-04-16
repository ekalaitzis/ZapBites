package com.example.zapbites.OrderProduct;

import com.example.zapbites.Orders.Orders;
import com.example.zapbites.Product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderProductTest {

    @Test
    public void testOrderProductEntity() {
        // Given
        Orders order = new Orders();
        order.setId(1L); // Assuming order ID 1 exists

        Product product = new Product();
        product.setId(1L); // Assuming product ID 1 exists

        OrderProduct orderProduct = new OrderProduct(1L, order, 5, product);

        // When - No action needed as this is just a simple entity test

        // Then
        assertNotNull(orderProduct.getId());
        assertEquals(1L, orderProduct.getId());
        assertEquals(order, orderProduct.getOrderId());
        assertEquals(5, orderProduct.getQuantity());
        assertEquals(product, orderProduct.getProduct_id());
    }
}
