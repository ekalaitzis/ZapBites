package com.example.zapbites.config;

import com.example.zapbites.CustomerAddress.Point.PointDeserializer;
import com.example.zapbites.CustomerAddress.Point.PointSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Point;

@Configuration
public class JacksonConfig {

//    @Bean
//    public ObjectMapper objectMapperLocalTime() {
//        // Create a custom JavaTimeModule with a custom serializer for LocalTime
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//
//        // Configure ObjectMapper with the custom JavaTimeModule
//        return JsonMapper.builder()
//                .addModule(javaTimeModule)
//                .build();
//    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Point.class, new PointSerializer());
        module.addDeserializer(Point.class, new PointDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
