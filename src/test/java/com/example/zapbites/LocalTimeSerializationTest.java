package com.example.zapbites;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalTimeSerializationTest {

    private final ObjectMapper objectMapper;

    public LocalTimeSerializationTest() {
        // Initialize ObjectMapper with JavaTimeModule
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @org.junit.jupiter.api.Test
    public void testLocalTimeSerialization() throws IOException {
        // Given
        LocalTime localTime = LocalTime.of(12, 30, 45);

        // When
        String json = objectMapper.writeValueAsString(localTime);
        LocalTime deserialized = objectMapper.readValue(json, LocalTime.class);

        // Then
        assertEquals(localTime, deserialized);
    }
}
