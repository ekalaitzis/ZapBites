package com.example.zapbites.config;

import com.example.zapbites.CustomerAddress.Point.PointDeserializer;
import com.example.zapbites.CustomerAddress.Point.PointSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Point;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // Create a custom JavaTimeModule with a custom serializer for LocalTime
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // Create a SimpleModule for custom Point serialization and deserialization
        SimpleModule pointModule = new SimpleModule();
        pointModule.addSerializer(Point.class, new PointSerializer());
        pointModule.addDeserializer(Point.class, new PointDeserializer());

        // Configure ObjectMapper with both custom modules
        return JsonMapper.builder()
                .addModule(javaTimeModule)
                .addModule(pointModule)
                .build();
    }
}
