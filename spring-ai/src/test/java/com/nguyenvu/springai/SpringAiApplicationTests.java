package com.nguyenvu.springai;

import com.nguyenvu.springai.service.AIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringAiApplicationTests {

    @Autowired(required = false)
    private AIService aiService;

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
        assertNotNull(aiService, "AIService should be loaded");
    }

    @Test
    void testAIServiceExists() {
        // Verify AIService bean is created
        assertNotNull(aiService, "AIService bean should exist");
    }

    @Test
    void testAIServiceAnswerNotNull() {
        // Test that AI service can handle basic query
        if (aiService != null) {
            try {
                String answer = aiService.answer("Hello");
                assertNotNull(answer, "Answer should not be null");
                assertFalse(answer.isEmpty(), "Answer should not be empty");
            } catch (Exception e) {
                // It's OK if API key is not configured in test environment
                assertTrue(e.getMessage().contains("API") || e.getMessage().contains("key"),
                        "Exception should be related to API key");
            }
        }
    }

    @Test
    void testInvalidQuestionHandling() {
        if (aiService != null) {
            // Test empty question
            assertThrows(IllegalArgumentException.class, () -> {
                aiService.answer("");
            }, "Should throw exception for empty question");

            // Test null question
            assertThrows(IllegalArgumentException.class, () -> {
                aiService.answer(null);
            }, "Should throw exception for null question");
        }
    }
}

