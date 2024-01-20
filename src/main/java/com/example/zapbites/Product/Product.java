package com.example.zapbites.Product;

import com.example.zapbites.Category.Category;
import com.example.zapbites.Ingredient.Ingredient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_seq")
    @SequenceGenerator(name = "business_seq", sequenceName = "business_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_category"))
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "product_ingredient",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    public Product() {
    }

    public Product(Long id, String name, String description, Category category, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", price=" + price + ", category=" + category + '}';
    }
}
