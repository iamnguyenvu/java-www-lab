package com.nguyenvu.thymeleafjpashopping.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads environment variables from .env file
 * Supports .env files in both project root and classpath
 * Variables can be referenced in application.properties as ${VARIABLE_NAME}
 */
public class DotEnvConfig implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // Try to load .env from project root first
        Resource resource = new FileSystemResource(".env");
        
        // If not found, try classpath
        if (!resource.exists()) {
            resource = new ClassPathResource(".env");
        }
        
        // If .env file exists, load it
        if (resource.exists()) {
            try {
                Map<String, Object> envProperties = loadEnvFile(resource);
                if (!envProperties.isEmpty()) {
                    environment.getPropertySources()
                            .addLast(new MapPropertySource("dotenv", envProperties));
                    System.out.println("✅ Loaded " + envProperties.size() + " variables from .env file");
                }
            } catch (IOException e) {
                System.err.println("⚠️ Warning: Could not load .env file: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ No .env file found, using system environment variables");
        }
    }

    private Map<String, Object> loadEnvFile(Resource resource) throws IOException {
        Map<String, Object> properties = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                // Parse KEY=VALUE format
                int separatorIndex = line.indexOf('=');
                if (separatorIndex > 0) {
                    String key = line.substring(0, separatorIndex).trim();
                    String value = line.substring(separatorIndex + 1).trim();
                    
                    // Remove quotes if present
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    } else if (value.startsWith("'") && value.endsWith("'")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    
                    properties.put(key, value);
                    System.out.println("  📝 Loaded: " + key);
                }
            }
        }
        
        return properties;
    }
}
