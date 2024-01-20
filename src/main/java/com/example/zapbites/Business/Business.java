package com.example.zapbites.Business;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "business")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_seq")
    @SequenceGenerator(name = "business_seq", sequenceName = "business_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;
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
    @NotBlank
    @Size(min = 10, max = 10)
    @Column(name = "tax_id_number", nullable = false)
    private String taxIdNumber;

    public Business() {
    }

    public Business(Long id, String companyName, String email, String password, String telephone, String taxIdNumber) {
        this.id = id;
        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.taxIdNumber = taxIdNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }

    @Override
    public String toString() {
        return "Business{" + "id=" + id + ", companyName='" + companyName + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", telephone='" + telephone + '\'' + ", taxIdNumber='" + taxIdNumber + '\'' + '}';
    }
}
