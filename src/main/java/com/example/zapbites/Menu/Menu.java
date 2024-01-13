package com.example.zapbites.Menu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Menu {
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
