package com.example.zapbites.Business.Security;

import com.example.zapbites.Business.Business;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Business user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public UserRole(Long id, Business user, Role role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public UserRole() {

    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", user=" + user +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && Objects.equals(user, userRole.user) && Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Business getUser() {
        return user;
    }

    public void setUser(Business user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}