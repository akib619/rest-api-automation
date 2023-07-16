package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ConfigReader {

    public static String getBaseUrl() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> config = objectMapper.readValue(new File("src/main/java/resources/config.json"), Map.class);
            return config.get("baseUrl");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config.json", e);
        }
    }
}
