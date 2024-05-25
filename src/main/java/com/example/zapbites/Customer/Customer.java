package com.example.zapbites.Customer;

import com.example.zapbites.CustomerAddress.CustomerAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;
    @NotBlank
    @Email
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    @Size(min = 6, max = 20)
    @Column(name = "password", nullable = false)
    private String password;
    @NotBlank
    @Size(min = 10, max = 14)
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @JsonIgnore
    @Transient
    @OneToMany(mappedBy = "customer")
    private List<CustomerAddress> customerAddresses;
    @Column(name = "role", nullable = false)
    private String role = "CUSTOMER";


}
