package com.example.zapbites.Menu;

import com.example.zapbites.Business.Business;
import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false, foreignKey = @ForeignKey(name ="fk_business_menu"))
    private Business business;

    public Menu(Long id, String name, Business business) {
        this.id = id;
        this.name = name;
        this.business = business;
    }

    public Menu(String name, Business business) {
        this.name = name;
        this.business = business;
    }

    public Menu() {

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
