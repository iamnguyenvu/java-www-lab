package com.nguyenvu.springai.controller;

import com.nguyenvu.springai.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {
    private final AIService aiService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "AI Flight Assistant");
        health.put("version", "1.0.0");
        
        try {
            boolean aiHealthy = aiService.isHealthy();
            health.put("ai_service", aiHealthy ? "UP" : "DOWN");
        } catch (Exception e) {
            health.put("ai_service", "DOWN");
            health.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(health);
    }
}
