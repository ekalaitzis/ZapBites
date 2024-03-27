package com.example.zapbites;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class TestHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readJsonFile(String filePath, Class<T> typeReference) throws IOException {
        InputStream inputStream = TestHelper.class.getResourceAsStream(filePath);
        return objectMapper.readValue(inputStream, typeReference);
    }
    public static String readJsonFile(String filePath) throws IOException {
        InputStream inputStream = TestHelper.class.getResourceAsStream(filePath);
        return new String(inputStream.readAllBytes());
    }

}