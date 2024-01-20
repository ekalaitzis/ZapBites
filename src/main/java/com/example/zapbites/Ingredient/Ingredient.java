package com.example.zapbites.Ingredient;

import com.example.zapbites.Product.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    @SequenceGenerator(name = "ingredient_seq", sequenceName = "ingredient_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private boolean vegan;
    private boolean spicy;
    private boolean glutenFree;
    @ManyToMany(mappedBy = "ingredients")
    private List<Product> products;

    public Ingredient() {
    }

    public Ingredient(Long id, String name, boolean vegan, boolean spicy, boolean glutenFree, List<Product> products) {
        this.id = id;
        this.name = name;
        this.vegan = vegan;
        this.spicy = spicy;
        this.glutenFree = glutenFree;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isSpicy() {
        return spicy;
    }

    public void setSpicy(boolean spicy) {
        this.spicy = spicy;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "id=" + id + ", name='" + name + '\'' + ", vegan=" + vegan + ", spicy=" + spicy + ", glutenFree=" + glutenFree + ", products=" + products + '}';
    }
}
