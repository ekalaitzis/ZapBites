package com.example.zapbites.OrderStatus;

import com.example.zapbites.Order.Order;
import jakarta.persistence.*;
import org.hibernate.Session;

import java.sql.Timestamp;

@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_status"), nullable = false)
    private Order orderId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum orderStatusEnum;
    @Column(name = "session", nullable = false)
    private Session session;
    @Column(name = "status_changed_at", nullable = false)
    private Timestamp statusChangedAt;

    public OrderStatus(Order orderId, Session session, Timestamp statusChangedAt) {
        this.orderId = orderId;
        this.session = session;
        this.statusChangedAt = statusChangedAt;
        this.orderStatusEnum = OrderStatusEnum.CART;
    }

    public OrderStatus(Long id, Order orderId, Session session, Timestamp statusChangedAt) {
        this.id = id;
        this.orderId = orderId;
        this.session = session;
        this.statusChangedAt = statusChangedAt;
        this.orderStatusEnum = OrderStatusEnum.CART;
    }

    public OrderStatus() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Timestamp getStatusChangedAt() {
        return statusChangedAt;
    }

    public void setStatusChangedAt(Timestamp statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }

    public OrderStatusEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }
}
