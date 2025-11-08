package com.nguyenvu.springai.ui;

import com.nguyenvu.springai.model.ChatMessage;
import com.nguyenvu.springai.service.AIService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@PageTitle("AI Flight Assistant")
@Route(value = "", layout = MainLayout.class)
public class ChatView extends VerticalLayout {

    private final AIService aiService;
    private final VerticalLayout chatContainer;
    private TextField inputField;
    private Button sendButton;
    private final List<ChatMessage> messageHistory;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public ChatView(AIService aiService) {
        this.aiService = aiService;
        this.messageHistory = new ArrayList<>();

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        addClassName("chat-view");

        // Header
        var header = createHeader();
        
        // Chat container with messages
        chatContainer = new VerticalLayout();
        chatContainer.addClassName("chat-container");
        chatContainer.setSizeFull();
        chatContainer.setPadding(true);
        chatContainer.setSpacing(true);
        chatContainer.getStyle()
                .set("overflow-y", "auto")
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("flex", "1");

        // Add welcome message
        addWelcomeMessage();

        // Input area
        var inputArea = createInputArea();
        inputArea.getStyle().set("background-color", "white");

        add(header, chatContainer, inputArea);
        expand(chatContainer);

        // Apply custom styles
        applyStyles();
    }

    private HorizontalLayout createHeader() {
        var header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.setSpacing(true);
        header.getStyle()
                .set("background", "linear-gradient(135deg, #667eea 0%, #764ba2 100%)")
                .set("color", "white")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");

        var icon = VaadinIcon.AIRPLANE.create();
        icon.setSize("32px");
        icon.getStyle().set("color", "white");

        var title = new H1("🛫 AI Flight Assistant");
        title.getStyle()
                .set("margin", "0")
                .set("font-size", "1.5rem")
                .set("color", "white");

        var subtitle = new Span("Hỗ trợ thông tin chuyến bay 24/7");
        subtitle.getStyle()
                .set("font-size", "0.875rem")
                .set("opacity", "0.9")
                .set("color", "white");

        var titleBox = new VerticalLayout(title, subtitle);
        titleBox.setSpacing(false);
        titleBox.setPadding(false);

        header.add(icon, titleBox);
        header.setAlignItems(Alignment.CENTER);
        return header;
    }

    private HorizontalLayout createInputArea() {
        var inputArea = new HorizontalLayout();
        inputArea.setWidthFull();
        inputArea.setPadding(true);
        inputArea.setSpacing(true);

        inputField = new TextField();
        inputField.setPlaceholder("Nhập câu hỏi về chuyến bay, giá vé, chính sách...");
        inputField.setWidthFull();
        inputField.setClearButtonVisible(true);
        inputField.setAutofocus(true);
        inputField.getStyle()
                .set("font-size", "1rem");
        
        inputField.addKeyPressListener(Key.ENTER, e -> sendMessage());

        sendButton = new Button("Gửi");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.setIcon(VaadinIcon.PAPERPLANE.create());
        sendButton.addClickListener(e -> sendMessage());
        sendButton.getStyle()
                .set("background", "linear-gradient(135deg, #667eea 0%, #764ba2 100%)")
                .set("border", "none");

        inputArea.add(inputField, sendButton);
        inputArea.setAlignItems(Alignment.END);
        inputArea.expand(inputField);

        return inputArea;
    }

    private void addWelcomeMessage() {
        String welcomeText = """
                Xin chào! 👋 Tôi là trợ lý AI của bạn.
                
                Tôi có thể giúp bạn về:
                • Thông tin chuyến bay và giá vé
                • Chính sách hủy và đổi vé
                • Hành lý và dịch vụ bổ sung
                • Hướng dẫn đặt vé và thanh toán
                
                Hãy hỏi tôi bất cứ điều gì! 😊
                """;
        
        ChatMessage welcomeMessage = new ChatMessage(welcomeText, false);
        messageHistory.add(welcomeMessage);
        chatContainer.add(createMessageBubble(welcomeMessage));
    }

    private void sendMessage() {
        String question = inputField.getValue();
        
        if (question == null || question.trim().isEmpty()) {
            showNotification("Vui lòng nhập câu hỏi", NotificationVariant.LUMO_ERROR);
            return;
        }

        // Disable input while processing
        inputField.setEnabled(false);
        sendButton.setEnabled(false);

        // Add user message
        ChatMessage userMessage = new ChatMessage(question, true);
        messageHistory.add(userMessage);
        chatContainer.add(createMessageBubble(userMessage));
        
        // Show typing indicator
        Div typingIndicator = createTypingIndicator();
        chatContainer.add(typingIndicator);

        // Clear input
        inputField.clear();

        // Get AI response in background thread
        new Thread(() -> {
            try {
                String answer = aiService.answer(question);
                
                // Update UI in Vaadin thread
                getUI().ifPresent(ui -> ui.access(() -> {
                    chatContainer.remove(typingIndicator);
                    
                    ChatMessage aiMessage = new ChatMessage(answer, false);
                    messageHistory.add(aiMessage);
                    chatContainer.add(createMessageBubble(aiMessage));
                    
                    inputField.setEnabled(true);
                    sendButton.setEnabled(true);
                    inputField.focus();
                }));
            } catch (Exception ex) {
                getUI().ifPresent(ui -> ui.access(() -> {
                    chatContainer.remove(typingIndicator);
                    
                    String errorMsg = "Xin lỗi, đã có lỗi xảy ra. ";
                    if (ex.getMessage() != null && ex.getMessage().contains("401")) {
                        errorMsg += "Vui lòng kiểm tra API key.";
                    } else {
                        errorMsg += ex.getMessage();
                    }
                    
                    ChatMessage errorMessage = new ChatMessage(errorMsg, false);
                    messageHistory.add(errorMessage);
                    chatContainer.add(createMessageBubble(errorMessage));
                    
                    inputField.setEnabled(true);
                    sendButton.setEnabled(true);
                }));
            }
        }).start();
    }

    private Div createMessageBubble(ChatMessage message) {
        Div bubble = new Div();
        bubble.getStyle()
                .set("max-width", "70%")
                .set("padding", "12px 16px")
                .set("border-radius", "18px")
                .set("margin-bottom", "8px")
                .set("word-wrap", "break-word")
                .set("white-space", "pre-wrap");

        Div messageContainer = new Div();
        messageContainer.setWidthFull();
        
        if (message.isUser()) {
            // User message - right aligned, blue
            bubble.getStyle()
                    .set("background", "linear-gradient(135deg, #667eea 0%, #764ba2 100%)")
                    .set("color", "white")
                    .set("margin-left", "auto")
                    .set("box-shadow", "0 2px 8px rgba(102, 126, 234, 0.3)");
            messageContainer.getStyle().set("text-align", "right");
        } else {
            // AI message - left aligned, white
            bubble.getStyle()
                    .set("background", "white")
                    .set("color", "var(--lumo-body-text-color)")
                    .set("margin-right", "auto")
                    .set("box-shadow", "0 2px 8px rgba(0, 0, 0, 0.1)")
                    .set("border", "1px solid var(--lumo-contrast-10pct)");
            messageContainer.getStyle().set("text-align", "left");
        }

        Span content = new Span(message.getContent());
        content.getStyle().set("display", "block");

        Span timestamp = new Span(message.getTimestamp().format(timeFormatter));
        timestamp.getStyle()
                .set("font-size", "0.75rem")
                .set("opacity", "0.7")
                .set("margin-top", "4px")
                .set("display", "block");

        bubble.add(content, timestamp);
        messageContainer.add(bubble);

        return messageContainer;
    }

    private Div createTypingIndicator() {
        Div indicator = new Div();
        indicator.getStyle()
                .set("padding", "12px 16px")
                .set("background", "white")
                .set("border-radius", "18px")
                .set("max-width", "70px")
                .set("box-shadow", "0 2px 8px rgba(0, 0, 0, 0.1)");

        Span dots = new Span("●●●");
        dots.getStyle()
                .set("animation", "typing 1.4s infinite")
                .set("font-size", "1.2rem")
                .set("color", "var(--lumo-contrast-50pct)");

        indicator.add(dots);
        return indicator;
    }

    private void scrollToBottom() {
        getUI().ifPresent(ui -> ui.getPage().executeJs(
                "setTimeout(() => { " +
                "const container = $0; " +
                "container.scrollTop = container.scrollHeight; " +
                "}, 100);", 
                chatContainer.getElement()
        ));
    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = new Notification(message, 3000);
        notification.addThemeVariants(variant);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.open();
    }

    private void applyStyles() {
        getUI().ifPresent(ui -> ui.getPage().addStyleSheet("""
                @keyframes typing {
                    0%, 60%, 100% { opacity: 0.3; }
                    30% { opacity: 1; }
                }
                """));
    }
}

