package com.example.zapbites.Customer;

import com.example.zapbites.CustomerAddress.CustomerAddress;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @Size(min = 6, max = 20)
    private String password;
    @NotBlank
    @Size(min = 10, max = 14)
    @Column(name = "telephone", nullable = false)
    private String telephone;

    public Customer(Long id, String firstName, String lastName, String email, String password, String telephone, Collection<CustomerAddress> customerAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.customerAddress = customerAddress;
    }

    public Customer(String firstName, String lastName, String email, String password, String telephone, Collection<CustomerAddress> customerAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.customerAddress = customerAddress;
    }

    public Customer() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "customer")
    private Collection<CustomerAddress> customerAddress;

    public Collection<CustomerAddress> getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Collection<CustomerAddress> customerAddress) {
        this.customerAddress = customerAddress;
    }
}
