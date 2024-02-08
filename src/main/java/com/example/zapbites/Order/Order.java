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
    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    public Order() {
    }

    public Order(Long id, Business businessId, Customer customerId, CustomerAddress customerAddressId, BigDecimal totalprice, Timestamp createdAt) {
        this.id = id;
        this.businessId = businessId;
        this.customerId = customerId;
        CustomerAddressId = customerAddressId;
        this.totalPrice = totalprice;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Business getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Business businessId) {
        this.businessId = businessId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public CustomerAddress getCustomerAddressId() {
        return CustomerAddressId;
    }

    public void setCustomerAddressId(CustomerAddress customerAddressId) {
        CustomerAddressId = customerAddressId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", businessId=" + businessId + ", customerId=" + customerId + ", CustomerAddressId=" + CustomerAddressId + ", totalPrice=" + totalPrice + ", createdAt=" + createdAt + '}';
    }
}
