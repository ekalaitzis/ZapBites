package com.example.zapbites.Ingredient;

import com.example.zapbites.Product.Product;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ingredient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    @SequenceGenerator(name = "ingredient_seq", sequenceName = "ingredient_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    private boolean vegan;
    private boolean spicy;
    private boolean glutenFree;
    @ManyToMany(mappedBy = "ingredients")
    private List<Product> products;


    @Override
    public String toString() {
        return "Ingredient{" + "id=" + id + ", name='" + name + '\'' + ", vegan=" + vegan + ", spicy=" + spicy + ", glutenFree=" + glutenFree +'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return vegan == that.vegan && spicy == that.spicy && glutenFree == that.glutenFree && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vegan, spicy, glutenFree);
    }
}
