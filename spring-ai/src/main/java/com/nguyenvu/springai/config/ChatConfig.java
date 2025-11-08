package com.nguyenvu.springai.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatConfig {

    // Mock ChatModel - active when MOCK_MODE=true
    @Bean
    @ConditionalOnProperty(name = "app.ai.mock-mode", havingValue = "true")
    public ChatModel mockChatModel() {
        return new ChatModel() {
            @Override
            public ChatResponse call(Prompt prompt) {
                // Get full prompt content including context
                String fullContent = prompt.getContents();
                String[] parts = fullContent.split("QUESTION:");
                String context = parts.length > 0 ? parts[0] : "";
                String question = parts.length > 1 ? parts[1].trim().toLowerCase() : fullContent.toLowerCase();
                
                // Check for specific booking codes or locations in question
                if (question.contains("abc123") || question.contains("abc 123")) {
                    if (context.contains("ABC123")) {
                        return new ChatResponse(List.of(new Generation(new AssistantMessage(
                            "Thông tin đặt chỗ ABC123:\n" +
                            "- Hành khách: John Doe\n" +
                            "- Hành trình: New York đến Los Angeles\n" +
                            "- Ngày bay: 2024-07-15"
                        ))));
                    }
                }
                
                if (question.contains("los angeles") || question.contains("la")) {
                    if (context.contains("Los Angeles")) {
                        return new ChatResponse(List.of(new Generation(new AssistantMessage(
                            "Có chuyến bay đến Los Angeles:\n" +
                            "- Mã đặt chỗ: ABC123\n" +
                            "- Từ: New York\n" +
                            "- Ngày: 2024-07-15"
                        ))));
                    }
                }
                
                if (question.contains("miami")) {
                    if (context.contains("Miami")) {
                        return new ChatResponse(List.of(new Generation(new AssistantMessage(
                            "Có chuyến bay đến Miami:\n" +
                            "- Mã đặt chỗ: DEF456\n" +
                            "- Từ: Chicago\n" +
                            "- Ngày: 2024-08-20"
                        ))));
                    }
                }
                
                // Policy questions
                if (question.contains("hủy") || question.contains("cancel")) {
                    return new ChatResponse(List.of(new Generation(new AssistantMessage(
                        "Chính sách hủy chuyến bay:\n" +
                        "- Cần liên hệ trước 24 giờ trước giờ khởi hành\n" +
                        "- Phí hủy áp dụng theo loại vé\n" +
                        "- Hoàn tiền trong vòng 7-14 ngày làm việc"
                    ))));
                }
                
                if (question.contains("đặt") || question.contains("book")) {
                    return new ChatResponse(List.of(new Generation(new AssistantMessage(
                        "Cách đặt vé:\n" +
                        "- Đặt vé online qua website\n" +
                        "- Liên hệ hotline\n" +
                        "- Thanh toán bằng thẻ hoặc chuyển khoản"
                    ))));
                }
                
                if (question.contains("hành lý") || question.contains("baggage")) {
                    return new ChatResponse(List.of(new Generation(new AssistantMessage(
                        "Quy định hành lý:\n" +
                        "- Hành lý xách tay: tối đa 7kg\n" +
                        "- Hành lý ký gửi: tối đa 23kg (hạng phổ thông)\n" +
                        "- Vượt quá sẽ tính phí bổ sung"
                    ))));
                }
                
                if (question.contains("hoàn tiền") || question.contains("refund")) {
                    return new ChatResponse(List.of(new Generation(new AssistantMessage(
                        "Chính sách hoàn tiền:\n" +
                        "- Hoàn tiền được xử lý trong 7-14 ngày làm việc\n" +
                        "- Tùy loại vé và điều kiện hủy\n" +
                        "- Có thể áp dụng phí xử lý"
                    ))));
                }
                
                // Default response
                return new ChatResponse(List.of(new Generation(new AssistantMessage(
                    "Xin chào! Tôi có thể giúp bạn về:\n" +
                    "- Thông tin đặt chỗ (mã ABC123, DEF456, GHI789)\n" +
                    "- Chính sách hủy vé và hoàn tiền\n" +
                    "- Quy định hành lý\n" +
                    "- Thông tin chuyến bay\n\n" +
                    "Bạn cần hỗ trợ gì?"
                ))));
            }
        };
    }

    // Using Google Gemini AI Studio API (configured via application.yml)
    // The ChatModel bean is auto-configured by Spring AI
}
