package com.nguyenvu.springai.controller;

import com.nguyenvu.springai.dto.AnswerResponse;
import com.nguyenvu.springai.dto.AskRequest;
import com.nguyenvu.springai.service.AIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AIController {
    private final AIService aiService;

    @PostMapping("/ask")
    public ResponseEntity<AnswerResponse> ask(@Valid @RequestBody AskRequest askRequest) {
        String ans = aiService.answer(askRequest.getQuestion());
        return ResponseEntity.ok(new AnswerResponse(ans));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return aiService.isHealthy() ? ResponseEntity.ok("OK") : ResponseEntity.internalServerError().body("DOWN");
    }
}
