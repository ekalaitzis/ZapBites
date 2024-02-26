package com.example.zapbites.OrderStatus;

import com.example.zapbites.Order.Order;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderStatusTest {

//    @Test
//    public void testOrderStatusEntity() {
//        // Given
//        Order order = new Order();
//        order.setId(1L); // Assuming order ID 1 exists
//
//        Session session = new Session(); // Assuming you have a valid Session object
//
//        Timestamp statusChangedAt = new Timestamp(System.currentTimeMillis());
//
//        OrderStatus orderStatus = new OrderStatus(1L, order, session, statusChangedAt);
//
//        // When - No action needed as this is just a simple entity test
//
//        // Then
//        assertNotNull(orderStatus.getId());
//        assertEquals(1L, orderStatus.getId());
//        assertEquals(order, orderStatus.getOrderId());
//        assertEquals(session, orderStatus.getSession());
//        assertEquals(statusChangedAt, orderStatus.getStatusChangedAt());
//        // Assuming default status is set correctly
//        assertEquals(OrderStatusEnum.CART, orderStatus.getOrderStatusEnum());
//    }
}
