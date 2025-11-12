package com.nguyenvu.thymeleafjpashopping.controller;

import com.nguyenvu.thymeleafjpashopping.service.ChatbotDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatbotController {
    
    private final ChatClient.Builder chatClientBuilder;
    private final ChatbotDataService chatbotDataService;
    private final ChatMemory chatMemory;
    
    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        try {
            String userMessage = request.get("message");
            String sessionId = request.getOrDefault("sessionId", "default");
            
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Tin nhắn không được để trống"));
            }
            
            log.info("Received chat message: {} from session: {}", userMessage, sessionId);
            
            // Lấy dữ liệu sản phẩm từ database
            String productKnowledge = chatbotDataService.getProductKnowledge();
            
            // Build chat client with memory and system prompt
            ChatClient chatClient = chatClientBuilder
                .defaultSystem("""
                    Bạn là trợ lý mua sắm thông minh của cửa hàng thương mại điện tử.
                    Nhiệm vụ của bạn là:
                    - Giúp khách hàng tìm sản phẩm phù hợp dựa trên dữ liệu cửa hàng
                    - Tư vấn về sản phẩm, giá cả, tồn kho dựa trên thông tin thực tế
                    - Hướng dẫn cách đặt hàng, thanh toán, vận chuyển
                    - Trả lời thắc mắc về chính sách đổi trả, bảo hành
                    - Luôn lịch sự, thân thiện và hữu ích
                    - Trả lời ngắn gọn, súc tích (2-4 câu)
                    - Nếu không chắc chắn, khuyên khách hàng liên hệ bộ phận hỗ trợ
                    - Sử dụng thông tin sản phẩm được cung cấp để đưa ra tư vấn chính xác
                    - Khi giới thiệu sản phẩm, chỉ đề cập sản phẩm CÓ SẴN trong danh sách
                    
                    """ + productKnowledge)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
            
            // Get response from AI
            String response = chatClient.prompt()
                .user(userMessage)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, sessionId))
                .call()
                .content();
            
            log.info("AI response: {}", response);
            
            return ResponseEntity.ok(Map.of(
                "message", response,
                "sessionId", sessionId
            ));
            
        } catch (Exception e) {
            log.error("Error processing chat message", e);
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Đã xảy ra lỗi khi xử lý tin nhắn. Vui lòng thử lại."));
        }
    }
    
    @DeleteMapping("/clear/{sessionId}")
    public ResponseEntity<Map<String, String>> clearHistory(@PathVariable String sessionId) {
        chatMemory.clear(sessionId);
        return ResponseEntity.ok(Map.of("message", "Đã xóa lịch sử chat"));
    }
}
