package com.example.zapbites.Business;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "business")
@AllArgsConstructor
@Getter
@Setter
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


    public Business() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Business business = (Business) o;
        return Objects.equals(id, business.id) && Objects.equals(companyName, business.companyName) && Objects.equals(email, business.email) && Objects.equals(password, business.password) && Objects.equals(telephone, business.telephone) && Objects.equals(taxIdNumber, business.taxIdNumber) && Objects.equals(role, business.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, email, password, telephone, taxIdNumber, role);
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", taxIdNumber='" + taxIdNumber + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
