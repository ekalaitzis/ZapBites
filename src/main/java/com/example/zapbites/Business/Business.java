package com.example.zapbites.Business;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Business {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String email;
    private String password;
    private String telephone;
    private String taxIdNumber;

    public Long getId() {
        return id;
    }

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

    public Business(String companyName, String email, String password, String telephone, String taxIdNumber) {
        this.companyName = companyName;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.taxIdNumber = taxIdNumber;
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


}
