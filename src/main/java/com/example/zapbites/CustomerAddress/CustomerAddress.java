package com.example.zapbites.CustomerAddress;

import com.example.zapbites.Customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.geo.Point;

@Entity
@Table(name = "customer_address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
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
    @Column(name = "primary_address")
    private boolean primary;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer"))
    private Customer customer;

}
