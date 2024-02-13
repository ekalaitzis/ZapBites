package com.example.zapbites.Menu;

import com.example.zapbites.Business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq")
    @SequenceGenerator(name = "menu_seq", sequenceName = "menu_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name = "fk_business_menu"))
    private Business business;

    public Menu() {
    }

    public Menu(Long id, String name, Business business) {
        this.id = id;
        this.name = name;
        this.business = business;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", name='" + name + '\'' + ", business=" + business + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id) && Objects.equals(name, menu.name) && Objects.equals(business, menu.business);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, business);
    }
}
