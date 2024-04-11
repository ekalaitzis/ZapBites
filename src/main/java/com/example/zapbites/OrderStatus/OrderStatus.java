package com.example.zapbites.OrderStatus;

import com.example.zapbites.Orders.Orders;
import jakarta.persistence.*;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_seq")
    @SequenceGenerator(name = "order_status_seq", sequenceName = "order_status_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_status"), nullable = false)
    private Orders orderId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum orderStatusEnum;
    @Column(name = "session")
    private Session session;
    @Column(name = "status_changed_at", nullable = false)
    private Timestamp statusChangedAt;

    public OrderStatus() {
    }

    public OrderStatus(Long id, Orders orderId, OrderStatusEnum orderStatusEnum, Session session, Timestamp statusChangedAt) {
        this.id = id;
        this.orderId = orderId;
        this.orderStatusEnum = orderStatusEnum;
        this.session = session;
        this.statusChangedAt = statusChangedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public OrderStatusEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(orderId, that.orderId) && orderStatusEnum == that.orderStatusEnum && Objects.equals(session, that.session) && Objects.equals(statusChangedAt, that.statusChangedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, orderStatusEnum, session, statusChangedAt);
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderStatusEnum=" + orderStatusEnum +
                ", session=" + session +
                ", statusChangedAt=" + statusChangedAt +
                '}';
    }
}
