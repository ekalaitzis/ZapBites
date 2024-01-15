package com.example.zapbites.Order;

import com.example.zapbites.Business.Business;
import com.example.zapbites.Customer.Customer;
import com.example.zapbites.CustomerAddress.CustomerAddress;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "business_id", foreignKey = @ForeignKey(name = "fk_business_order_id"), nullable = false)
    private Business businessId;
    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_customer_order_id"), nullable = false)
    private Customer customerId;
    @ManyToOne
    @JoinColumn(name = "customer_address_id", foreignKey = @ForeignKey(name = "fk_customer_address"), nullable = false)
    private CustomerAddress CustomerAddressId;
    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    public Order(Business businessId, Customer customerId, CustomerAddress customerAddressId, BigDecimal totalprice, Timestamp createdAt) {
        this.businessId = businessId;
        this.customerId = customerId;
        CustomerAddressId = customerAddressId;
        this.totalPrice = totalprice;
        this.createdAt = createdAt;
    }

    public Order(Long id, Business businessId, Customer customerId, CustomerAddress customerAddressId, BigDecimal totalprice, Timestamp createdAt) {
        this.id = id;
        this.businessId = businessId;
        this.customerId = customerId;
        CustomerAddressId = customerAddressId;
        this.totalPrice = totalprice;
        this.createdAt = createdAt;
    }

    public Order() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
