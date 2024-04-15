package com.example.zapbites.Business.Security;

import com.example.zapbites.Business.Business;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BusinessUserDetails implements UserDetails {
    private final Business business;
    private final Set<Role> roles;


    @Override
    public String getUsername() {
        return business.getEmail();
    }

    @Override
    public String getPassword() {
        return business.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Business getBusiness() {
        return business;
    }
}