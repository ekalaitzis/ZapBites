package com.example.zapbites.Product;

import com.example.zapbites.Category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_category"))
    private Category category;

    public String getName() {
        return name;
    }


    public Product(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Product(Long id, String name, String description, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Product() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
