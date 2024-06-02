package com.example.zapbites.Order;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Customer.Customer;
import com.example.zapbites.CustomerAddress.CustomerAddress;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "business_id", foreignKey = @ForeignKey(name = "fk_business_order_id"), nullable = false)
    private Business businessId;
    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_order_id"), nullable = false)
    private Customer customerId;
    @OneToOne
    @JoinColumn(name = "customer_address_id", foreignKey = @ForeignKey(name = "fk_customer_address"), nullable = false)
    private CustomerAddress CustomerAddressId;
    @Positive
    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

}
