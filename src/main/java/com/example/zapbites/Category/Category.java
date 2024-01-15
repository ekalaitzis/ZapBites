package com.example.zapbites.Category;

import com.example.zapbites.Menu.Menu;
import jakarta.persistence.*;


@Entity
@Table(name = "catetgory")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false, foreignKey = @ForeignKey(name = "fk_menu"))
    private Menu menu;

    public Category(String name, Menu menu) {
        this.name = name;
        this.menu = menu;
    }

    public Category(Long id, String name, Menu menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    public Category() {

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
}
