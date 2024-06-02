package com.example.zapbites.OrderStatus;

import com.example.zapbites.Order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "order_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_seq")
    @SequenceGenerator(name = "order_status_seq", sequenceName = "order_status_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_status"), nullable = false)
    private Order orderId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum orderStatusEnum;
    @Column(name = "session")
    private String sessionId;
    @Column(name = "status_changed_at", nullable = false)
    private Timestamp statusChangedAt;

}


