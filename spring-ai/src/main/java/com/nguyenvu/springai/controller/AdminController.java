package com.nguyenvu.springai.controller;

import com.nguyenvu.springai.service.DocumentIngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final DocumentIngestionService ingestionService;

    public AdminController(DocumentIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/reindex")
    public ResponseEntity<Map<String, String>> reindex() {
        try {
            ingestionService.reindex();
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Documents reindexed successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/clear-store")
    public ResponseEntity<Map<String, String>> clearStore() {
        try {
            ingestionService.clearStore();
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Vector store cleared successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/ingest")
    public ResponseEntity<Map<String, String>> ingest() {
        try {
            ingestionService.ingestDocuments();
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Documents ingested successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", e.getMessage()
            ));
        }
    }
}
