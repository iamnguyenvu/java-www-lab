# 🛍️ Thymeleaf JPA Shopping - E-commerce Platform

Một nền tảng thương mại điện tử hoàn chỉnh được xây dựng bằng **Spring Boot**, **Thymeleaf**, **JPA/Hibernate**, tích hợp **Spring AI Chatbot** với dữ liệu thực từ database và validation đầy đủ.

## ✨ Tính năng chính

### 🎯 Cho Khách hàng (Customer)
- ✅ **Đăng ký/Đăng nhập** với validation đầy đủ
- ✅ **Xem danh sách sản phẩm** theo danh mục, tìm kiếm, lọc giá
- ✅ **Giao diện hiện đại**: Product cards với hình ảnh, gradient headers, badges
- ✅ **Giỏ hàng thông minh**: Thêm/xóa/cập nhật số lượng, kiểm tra tồn kho tự động
- ✅ **Đặt hàng** với validation địa chỉ và số điện thoại
- ✅ **Theo dõi đơn hàng** với chi tiết đầy đủ
- ✅ **AI Chatbot nổi**: Floating widget ở mọi trang, tư vấn dựa trên dữ liệu thật

### 👨‍💼 Cho Admin
- ✅ **Quản lý sản phẩm**: CRUD đầy đủ với validation, form hiện đại
- ✅ **Chỉnh sửa sản phẩm**: Fix lazy loading category, validation errors
- ✅ **Quản lý khách hàng**: Xem danh sách, thông tin chi tiết
- ✅ **Quản lý đơn hàng**: Xem tất cả đơn hàng, cập nhật trạng thái
- ✅ **Dashboard** thống kê (sẵn sàng mở rộng)

### 🤖 AI Chatbot với Google Gemini 2.0 Flash
- 💬 **Floating chatbot**: Nút tròn nổi ở góc dưới phải mọi trang
- 🇻🇳 **Hỗ trợ tiếng Việt** hoàn toàn
- 🎯 **Tư vấn thông minh**: Dựa trên dữ liệu sản phẩm thực từ database
- 📊 **Thông tin chính xác**: Tên, giá, tồn kho, danh mục
- � **Lưu lịch sử chat** theo session với ChatMemory
- 📱 **Responsive**: Tối ưu cho mobile và desktop
- ✨ **UI hiện đại**: Typing indicator, smooth animations

## 🛠️ Công nghệ sử dụng

### Backend
- **Spring Boot 3.5.6** - Framework chính
- **Spring Security** - Authentication & Authorization (ROLE-based)
- **Spring Data JPA** - ORM với Hibernate 6.6.29
- **Spring AI 1.1.0-RC1** - Tích hợp AI Gemini với Google GenAI
- **Spring Validation** - Bean Validation với jakarta.validation
- **MariaDB 11.6.2** - Relational Database
- **Lombok** - Giảm boilerplate code

### Frontend
- **Thymeleaf 3.1.3** - Server-side template engine
- **HTML5/CSS3** - Semantic markup, modern styling
- **JavaScript (Vanilla)** - Interactive chatbot, form handling
- **Modern CSS**: Gradients, transitions, flex/grid layouts
- **Icons**: SVG inline, emoji unicode

### AI Integration
- **Spring AI 1.1.0-RC1** - Chat Client API
- **Google GenAI**: Gemini 2.0 Flash model
- **MessageChatMemoryAdvisor**: Conversation history management
- **MessageWindowChatMemory**: Auto-configured chat memory
- **ChatbotDataService**: Database integration for product knowledge

## 📋 Yêu cầu hệ thống

- **Java 21+**
- **Maven 3.8+**
- **MariaDB/MySQL 5.7+**
- **Google Gemini API Key** (miễn phí - 1,500 requests/ngày)

## 🚀 Cài đặt & Chạy

### 1. Clone repository
```bash
git clone <repository-url>
cd thymeleaf-jpa-shopping
```

### 2. Tạo database
```sql
CREATE DATABASE shopping CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Cấu hình database
Chỉnh sửa `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/shopping
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Import dữ liệu mẫu
```bash
mysql -u root -p shopping < scripts/shopping.sql
```

### 5. Cấu hình Gemini API
Xem hướng dẫn chi tiết trong [GEMINI_SETUP.md](GEMINI_SETUP.md)

**Tóm tắt:**
1. Lấy API key từ: https://aistudio.google.com/apikey
2. Set biến môi trường hoặc cập nhật `application.properties`

### 6. Compile & Run
```bash
./mvnw clean compile
./mvnw spring-boot:run
```

Hoặc với Gemini API key:
```bash
# Windows PowerShell
$env:GEMINI_API_KEY="YOUR_API_KEY"; ./mvnw spring-boot:run

# Linux/Mac
GEMINI_API_KEY="YOUR_API_KEY" ./mvnw spring-boot:run
```

### 7. Truy cập ứng dụng
- **URL**: http://localhost:8088
- **Admin**: admin / 123456
- **Customer**: nguyenvana / 123456

## 📁 Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/nguyenvu/thymeleafjpashopping/
│   │   ├── controller/          # REST & MVC Controllers
│   │   │   ├── ChatbotController.java       # AI Chatbot API
│   │   │   ├── HomeController.java          # Home, Login, Register
│   │   │   ├── ProductController.java       # Product CRUD
│   │   │   ├── CartController.java          # Shopping Cart
│   │   │   ├── OrderController.java         # Order Management
│   │   │   └── CustomerController.java      # Customer Management
│   │   ├── dto/                 # Data Transfer Objects với Validation
│   │   ├── model/               # JPA Entities
│   │   ├── repository/          # Spring Data JPA Repositories
│   │   ├── service/             # Business Logic Layer
│   │   ├── security/            # Spring Security Config
│   │   └── util/                # Utilities
│   └── resources/
│       ├── templates/           # Thymeleaf Templates
│       │   ├── fragments/       # Reusable components
│       │   │   ├── navbar.html          # Navigation bar
│       │   │   └── chatbot.html         # AI Chatbot UI
│       │   ├── cart/            # Cart & Checkout pages
│       │   ├── product/         # Product pages
│       │   ├── order/           # Order pages
│       │   ├── customer/        # Customer pages
│       │   └── *.html           # Public pages
│       ├── static/              # CSS, JS, Images
│       └── application.properties
└── test/                        # Unit & Integration Tests
```

## 🔐 Bảo mật

- ✅ **BCrypt** password hashing
- ✅ **CSRF Protection** enabled
- ✅ **Role-based Access Control** (ADMIN, CUSTOMER)
- ✅ **Session Management** với Spring Security
- ✅ **Input Validation** trên tất cả forms
- ✅ **XSS Protection** với Thymeleaf escaping

## ✅ Validation Rules

### Customer Registration
- **Name**: 2-100 ký tự
- **Username**: 3-50 ký tự, chỉ chữ/số/underscore
- **Email**: Format hợp lệ
- **Phone**: 10-11 chữ số
- **Password**: Tối thiểu 6 ký tự

### Product Management
- **Name**: 2-200 ký tự
- **Price**: > 0, max 10 chữ số + 2 decimal
- **Stock**: >= 0
- **Category**: Required

### Checkout
- **Shipping Address**: 10-255 ký tự
- **Phone**: 10-11 chữ số
- **Stock Validation**: Tự động kiểm tra khi add to cart

## 🎨 Screenshots

### Homepage
![Homepage](screenshots/home.png)

### Products
![Products](screenshots/products.png)

### Shopping Cart
![Cart](screenshots/cart.png)

### AI Chatbot
![Chatbot](screenshots/chatbot.png)

## 📊 Database Schema

### Main Tables
- **customers** - Thông tin khách hàng
- **products** - Danh sách sản phẩm
- **categories** - Danh mục sản phẩm
- **orders** - Đơn hàng
- **order_lines** - Chi tiết đơn hàng
- **comments** - Đánh giá sản phẩm (ready for feature)

### Relationships
```
customers (1) ---> (*) orders
orders (1) ---> (*) order_lines
products (*) <--- (1) categories
products (1) ---> (*) order_lines
products (1) ---> (*) comments
customers (1) ---> (*) comments
```

## 🧪 Testing

### Manual Testing Flow
1. **Registration**: Đăng ký tài khoản mới
2. **Login**: Đăng nhập
3. **Browse Products**: Xem sản phẩm, tìm kiếm, lọc
4. **Add to Cart**: Thêm sản phẩm vào giỏ
5. **Update Cart**: Thay đổi số lượng
6. **Checkout**: Điền thông tin giao hàng
7. **Order Tracking**: Xem chi tiết đơn hàng
8. **AI Chat**: Test chatbot

### Test Accounts
```
Admin:
- Username: admin
- Password: 123456

Customer:
- Username: nguyenvana
- Password: 123456
```

## 🚧 Future Enhancements

- [ ] Payment gateway integration (VNPay, Momo)
- [ ] Email notifications
- [ ] Product reviews & ratings UI
- [ ] Wishlist functionality
- [ ] Advanced search with Elasticsearch
- [ ] Order status tracking với timeline
- [ ] Dashboard analytics cho admin
- [ ] Multi-language support (i18n)
- [ ] Progressive Web App (PWA)

## 📝 API Endpoints

### Public Endpoints
- `GET /` - Homepage
- `GET /login` - Login page
- `GET /register` - Registration page
- `GET /products` - Product listing
- `GET /products/{id}` - Product detail
- `POST /api/chat` - AI Chatbot (no auth required)

### Customer Endpoints (Authenticated)
- `GET /cart` - View cart
- `POST /cart/add/{id}` - Add to cart
- `POST /cart/update/{id}` - Update quantity
- `POST /cart/remove/{id}` - Remove from cart
- `GET /cart/checkout` - Checkout page
- `POST /cart/checkout` - Process order
- `GET /orders` - My orders
- `GET /orders/{id}` - Order detail

### Admin Endpoints (ADMIN role)
- `GET /products/new` - New product form
- `POST /products/new` - Create product
- `GET /products/edit/{id}` - Edit product form
- `POST /products/edit/{id}` - Update product
- `POST /products/delete` - Delete product
- `GET /customers` - Customer list
- `GET /customers/{id}` - Customer detail

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is for educational purposes.

## 👨‍💻 Author

Nguyễn Vũ

## 📞 Support

- Email: support@example.com
- GitHub Issues: [Create an issue](https://github.com/...)

---

**Note**: Đây là project demo/học tập. Cho production, cần thêm:
- HTTPS/SSL
- Rate limiting
- Logging & Monitoring
- Backup strategy
- Load balancing
- CDN for static assets
- Environment-specific configs
