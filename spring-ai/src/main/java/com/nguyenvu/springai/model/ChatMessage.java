package com.nguyenvu.springai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String content;
    private boolean isUser; // true if from user, false if from AI
    private LocalDateTime timestamp;

    public ChatMessage(String content, boolean isUser) {
        this.content = content;
        this.isUser = isUser;
        this.timestamp = LocalDateTime.now();
    }
}
