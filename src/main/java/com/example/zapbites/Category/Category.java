package com.example.zapbites.Category;

import com.example.zapbites.Menu.Menu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", nullable = false, foreignKey = @ForeignKey(name = "fk_menu"))
    private Menu menu;
    public Category() {
    }
    public Category(Long id, String name, Menu menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menu=" + menu +
                '}';
    }
}
