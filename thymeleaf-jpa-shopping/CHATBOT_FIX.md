# Chatbot Fix Documentation

## Vấn đề ban đầu
Chatbot trả về lỗi: "Xin lỗi, đã có lỗi xảy ra"

## Nguyên nhân
CSRF protection của Spring Security chặn POST request tới `/api/chat` endpoint vì không có CSRF token.

## Giải pháp
Thêm CSRF exception cho API endpoint trong `SecurityConfig.java`:

```java
.csrf(csrf -> csrf
    .ignoringRequestMatchers("/api/chat/**"))
```

## Các thành phần chatbot

### 1. Backend Components
- **ChatbotController** (`/api/chat` POST endpoint)
  - Nhận message và sessionId
  - Xây dựng context từ database (sản phẩm, danh mục, chính sách)
  - Sử dụng Spring AI với Google Gemini
  - Quản lý chat memory theo session
  
- **ChatbotDataService**
  - Lấy dữ liệu sản phẩm realtime từ database
  - Format knowledge base cho AI
  - Support search và filter

### 2. Frontend Components
- **chatbot.html** (Thymeleaf fragment)
  - Floating button với icon chat
  - Chat window popup với header/messages/input
  - JavaScript xử lý UI interaction và API calls
  - Typing indicator animation
  - Auto-scroll messages
  - Session persistence

### 3. Configuration
- **application.properties**
  ```properties
  spring.ai.google.genai.api-key=${GEMINI_API_KEY}
  spring.ai.google.genai.chat.options.model=gemini-2.0-flash
  spring.ai.google.genai.chat.options.temperature=0.7
  spring.ai.google.genai.chat.options.max-output-tokens=2048
  ```

- **.env** (contains API key)
  ```
  GEMINI_API_KEY=AIzaSy...
  ```

- **SecurityConfig.java**
  ```java
  .requestMatchers("/api/chat/**").permitAll()
  .csrf(csrf -> csrf.ignoringRequestMatchers("/api/chat/**"))
  ```

### 4. Dependencies (pom.xml)
```xml
<spring-ai.version>1.1.0-RC1</spring-ai.version>

<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-google-genai</artifactId>
</dependency>
```

## Testing

### Manual Test (Browser)
1. Mở trang bất kỳ có chatbot widget (vd: /products)
2. Click floating button góc phải dưới
3. Nhập tin nhắn: "Xin chào"
4. Kiểm tra response từ AI

### API Test (PowerShell)
Sử dụng script `test-chatbot.ps1`:
```powershell
.\test-chatbot.ps1
```

Hoặc test thủ công:
```powershell
$body = @{
    message = "Xin chào"
    sessionId = "test-123"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8088/api/chat" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body
```

### Expected Response
```json
{
  "message": "Xin chào! Tôi là trợ lý mua sắm...",
  "sessionId": "test-123"
}
```

## Features
- ✅ Real-time product data từ database
- ✅ Context-aware responses với chat memory
- ✅ Session management (multiple users)
- ✅ Typing indicator animation
- ✅ Auto-scroll messages
- ✅ Mobile responsive
- ✅ Accessible (keyboard support)
- ✅ Error handling graceful
- ✅ CSRF protection bypass cho API

## Common Issues

### Issue 1: "405 Method Not Allowed"
**Cause**: CSRF protection blocking POST  
**Fix**: Đã thêm `.csrf(csrf -> csrf.ignoringRequestMatchers("/api/chat/**"))`

### Issue 2: "API key not found"
**Cause**: Missing GEMINI_API_KEY  
**Fix**: Kiểm tra file `.env` có key chưa

### Issue 3: "Cannot connect to server"
**Cause**: Server chưa chạy hoặc port sai  
**Fix**: Start app với `./mvnw spring-boot:run`, kiểm tra port 8088

### Issue 4: Chat memory not working
**Cause**: SessionId không persist  
**Fix**: Frontend đã tạo sessionId duy nhất khi load: `'session-' + Date.now()`

## Next Steps
- [ ] Add rate limiting để tránh spam
- [ ] Log chat conversations để phân tích
- [ ] Add feedback buttons (thumbs up/down)
- [ ] Support file upload (images của sản phẩm)
- [ ] Multi-language support
- [ ] Voice input/output

## Files Changed
1. `src/main/java/com/nguyenvu/thymeleafjpashopping/security/SecurityConfig.java` - CSRF bypass
2. `test-chatbot.ps1` - Test script (NEW)
3. `CHATBOT_FIX.md` - Documentation (NEW)
