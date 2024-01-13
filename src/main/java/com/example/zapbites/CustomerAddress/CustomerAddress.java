package com.example.zapbites.CustomerAddress;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CustomerAddress {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
