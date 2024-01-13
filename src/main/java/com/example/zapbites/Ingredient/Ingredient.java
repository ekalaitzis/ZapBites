package com.example.zapbites.Ingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ingredient {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
