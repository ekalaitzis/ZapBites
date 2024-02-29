package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.awt.*;
import java.util.Objects;

@Entity
@Table(name = "customer_address")
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_address_seq")
    @SequenceGenerator(name = "customer_address_seq", sequenceName = "customer_address_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "address", nullable = false, length = 65535)
    private String address;
    @Column(name = "geolocation", nullable = false)
    private Point geolocation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_customer"))
    private Customer customer;

    public CustomerAddress() {
    }

    public CustomerAddress(Long id, String address, Point geolocation, Customer customer) {
        this.id = id;
        this.address = address;
        this.geolocation = geolocation;
        this.customer = customer;
    }

    public Long getId() {
        return id;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerAddress{" + "id=" + id + ", address='" + address + '\'' + ", geolocation=" + geolocation + ", customer=" + customer + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAddress that = (CustomerAddress) o;
        return Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(geolocation, that.geolocation) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, geolocation, customer);
    }
}
