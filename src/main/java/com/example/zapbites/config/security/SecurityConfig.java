package com.example.zapbites.config.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private final UserDetailsService businessUserDetailsService;


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(c -> c
                .requestMatchers("/*/register").permitAll()
                .requestMatchers("/business/***").hasRole("BUSINESS")
                .requestMatchers("/business_schedule/***").hasRole("BUSINESS")
                .requestMatchers("/category/***").hasRole("BUSINESS")
                .requestMatchers("/menu/***").hasRole("BUSINESS")
                .requestMatchers("/product/***").hasRole("BUSINESS")
                .requestMatchers("/ingredient/***").hasRole("BUSINESS")
                .requestMatchers("/orders/***").hasAnyRole("BUSINESS","CUSTOMER")
                .requestMatchers("/order_product/***").hasAnyRole("BUSINESS","CUSTOMER")
                .requestMatchers("/order_status/***").hasAnyRole("BUSINESS","CUSTOMER")
                .requestMatchers("/search/***").hasAnyRole("BUSINESS","CUSTOMER")
                .requestMatchers("/customer/***").hasRole("CUSTOMER")
                .requestMatchers("/customer_address/***").hasRole("CUSTOMER")
                .anyRequest()
                .authenticated());

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
