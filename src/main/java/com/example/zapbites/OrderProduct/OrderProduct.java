package com.example.zapbites.OrderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderProduct {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
