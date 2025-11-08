package com.nguyenvu.springai.service;

import com.nguyenvu.springai.model.BookingDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIService {
    private static final Logger log = LoggerFactory.getLogger(AIService.class);

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final BookingService bookingService;

    public AIService(ChatClient.Builder builder, VectorStore vectorStore, BookingService bookingService) {
        this.vectorStore = vectorStore;
        this.bookingService = bookingService;
        this.chatClient = builder
                .defaultSystem("""
                        You are a professional AI assistant for flight booking and airline services.
                        Answer in Vietnamese clearly and concisely.
                        Only use the information from the provided context.
                        """)
                .build();
        log.info("AIService initialized with RAG enabled");
    }

    public String answer(String question) {
        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Cau hoi khong duoc de trong");
        }

        log.info("Processing question with RAG: {}", question);

        try {
            // Retrieve relevant documents
            SearchRequest searchRequest = SearchRequest.builder()
                    .query(question)
                    .topK(5)
                    .similarityThreshold(0.3)
                    .build();

            List<Document> relevantDocs = vectorStore.similaritySearch(searchRequest);
            log.info("Found {} relevant documents", relevantDocs.size());

            // Build context from retrieved docs
            String ragContext = relevantDocs.stream()
                    .map(doc -> String.format("Nguon [%s]: %s",
                            doc.getMetadata().getOrDefault("source", "unknown"),
                            doc.getFormattedContent()))
                    .collect(Collectors.joining("\n\n---\n\n"));

            // Add booking data
            StringBuilder bookingContext = new StringBuilder();
            List<BookingDetails> bookings = bookingService.findAll();
            if (!bookings.isEmpty()) {
                bookingContext.append("\nTHONG TIN DAT CHO:\n");
                for (BookingDetails booking : bookings) {
                    bookingContext.append(String.format(
                            "- Ma: %s | Khach: %s %s | %s -> %s | %s\n",
                            booking.getNumber(),
                            booking.getFirstName(),
                            booking.getLastName(),
                            booking.getFrom(),
                            booking.getTo(),
                            booking.getDate()
                    ));
                }
            }

            // Create prompt
            String prompt = String.format("""
                    CONTEXT:
                    %s

                    %s

                    CAU HOI: %s

                    Huong dan tra loi:
                    - Chi dua tren CONTEXT o tren
                    - Tra loi ngan gon, ro rang, co the dung bullet
                    - Neu thieu thong tin, hay noi ro rang la khong co trong context
                    """,
                    ragContext.isEmpty() ? "Khong tim thay thong tin lien quan." : ragContext,
                    bookingContext.toString(),
                    question);

            // Get AI response
            return chatClient.prompt().user(prompt).call().content();

        } catch (Exception e) {
            log.error("Error processing question with RAG", e);
            return "Xin loi, da xay ra loi khi xu ly cau hoi. Vui long thu lai sau.";
        }
    }

    public boolean isHealthy() {
        try {
            chatClient.prompt().user("test").call().content();
            return true;
        } catch (Exception e) {
            log.error("Health check failed", e);
            return false;
        }
    }
}
