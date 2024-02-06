package com.example.zapbites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ZapbitesApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZapbitesApplication.class, args);
    }

}
