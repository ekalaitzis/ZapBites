package com.example.zapbites.Business;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "business")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_seq")
    @SequenceGenerator(name = "business_seq", sequenceName = "business_seq", allocationSize = 1)
    private Long id;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @NotBlank
    @Size(min = 10, max = 14)
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @NotBlank
    @Column(name = "tax_id_number", nullable = false)
    private String taxIdNumber;
    @Column(name = "role", nullable = false)
    private String role = "BUSINESS";


}
