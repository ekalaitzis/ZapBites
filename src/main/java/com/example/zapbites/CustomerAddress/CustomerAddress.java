package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import jakarta.persistence.*;

import java.awt.*;

@Entity
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "address", nullable = false, length = 65535)
    private String address;
    @Column(name = "geolocation", nullable = false)
    private Point geolocation;
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_customer"))
    private Customer customer;

    public CustomerAddress() {
    }

    public CustomerAddress(String address, Point geolocation, Integer customerId, Customer customer) {
        this.address = address;
        this.geolocation = geolocation;
        this.customerId = customerId;
        this.customer = customer;
    }

    public CustomerAddress(Long id, String address, Point geolocation, Integer customerId, Customer customer) {
        this.id = id;
        this.address = address;
        this.geolocation = geolocation;
        this.customerId = customerId;
        this.customer = customer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Point getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Point geolocation) {
        this.geolocation = geolocation;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }
}
