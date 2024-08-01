package com.example.zapbites.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService businessUserDetailsService;


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(c -> c
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/v2/api-docs").permitAll()
                .requestMatchers("/v3/api-docs").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-resources").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/configuration/ui").permitAll()
                .requestMatchers("/configuration/security").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/*/register").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/*/register/save").permitAll()
                .requestMatchers("/*/login").permitAll()
                .requestMatchers("/*/businesses/login").permitAll()
                .requestMatchers("/*/index").permitAll()
                .requestMatchers("/*/create").permitAll()
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

        http.formLogin(form -> form
                        .loginPage("/businesses/login")
                        .defaultSuccessUrl("/businesses/home", true)
                        .loginProcessingUrl("/businesses/login")
                        .failureForwardUrl("/businesses/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AndRequestMatcher(
                                new AntPathRequestMatcher("/businesses/logout", "GET")
                        )).logoutSuccessUrl("/businesses/login")
                        .permitAll()
                );

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
