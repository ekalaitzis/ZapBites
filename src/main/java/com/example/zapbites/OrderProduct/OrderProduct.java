package com.example.zapbites.OrderProduct;

import com.example.zapbites.Order.Order;
import com.example.zapbites.Product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order"), nullable = false)
    private Order orderId;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product"))
    private Product product_id;

    public OrderProduct(Order orderId, Integer quantity, Product product_id) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public OrderProduct(Long id, Order orderId, Integer quantity, Product product_id) {
        this.id = id;
        this.orderId = orderId;
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public OrderProduct() {

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }
}
