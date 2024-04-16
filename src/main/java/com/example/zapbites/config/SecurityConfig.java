package com.example.zapbites.config;

import com.example.zapbites.Business.Security.BusinessUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final BusinessUserDetailsService businessUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                .requestMatchers(HttpMethod.POST, "/business")
//                .permitAll()
//                .requestMatchers("/business/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated())
//                .formLogin(withDefaults());

//
        http
                .authorizeHttpRequests(request -> request
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());
        return http.build();

//        http.authorizeHttpRequests((auth) -> auth
//                        .requestMatchers(HttpMethod.POST, "/business").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/business/**").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/business/{id}").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/business/{id}").authenticated()
//                )
//                .formLogin()
//                .and()
//                .logout();

//        return http.build();


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder){
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("sarah1")
                .password(passwordEncoder.encode("12345"))
                .roles()
                .build();
        return new InMemoryUserDetailsManager(sarah);
    }

}
