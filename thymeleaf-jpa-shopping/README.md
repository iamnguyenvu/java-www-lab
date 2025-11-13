<div align="center">

# 🛍️ Thymeleaf JPA Shopping

### Modern E-commerce Platform with AI Chatbot

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

[English](#english) | [Tiếng Việt](#tiếng-việt)

</div>

---

<a name="english"></a>
## 📖 English Documentation

### 🌟 Overview

A full-featured e-commerce platform built with **Spring Boot**, **Thymeleaf**, **JPA/Hibernate**, and **Spring AI**. Features a modern UI with cyan theme (#00BCD4), responsive design, and an intelligent chatbot powered by Google Gemini AI.

### ✨ Key Features

#### 👤 Customer Features
- **User Authentication**: Register/Login with full validation
- **Product Browsing**: View products by category, search, filter by price
- **Modern UI**: Product cards with images, gradient headers, status badges
- **Smart Shopping Cart**: Add/remove/update quantities with automatic stock validation
- **Checkout**: Place orders with address and phone validation
- **Order Tracking**: View order history with detailed information
- **AI Chatbot**: Floating widget on every page with real-time product recommendations

#### 🔐 Admin Features
- **Product Management**: Full CRUD operations with validation
- **Customer Management**: View customer list and detailed information
- **Order Management**: View all orders and update statuses
- **Dashboard**: Statistics and analytics (ready for expansion)
- **Role-based Access**: Secure endpoints with Spring Security

#### 🤖 AI Chatbot (Google Gemini 2.0 Flash)
- 💬 **Floating Widget**: Bottom-right corner on all pages
- 🇻🇳 **Vietnamese Support**: Full localization
- 🎯 **Smart Recommendations**: Based on real database product data
- 📊 **Accurate Information**: Name, price, stock, categories
- 💾 **Chat History**: Session-based conversation memory
- 📱 **Responsive Design**: Optimized for mobile and desktop
- ✨ **Modern UI**: Typing indicator, smooth animations

### 🛠️ Technology Stack

**Backend:**
- Spring Boot 3.5.6
- Spring Security (Role-based authentication)
- Spring Data JPA with Hibernate
- Spring AI 1.1.0-RC1 (Google GenAI integration)
- MariaDB 11.6.2
- Maven
- Lombok

**Frontend:**
- Thymeleaf 3.1.3
- HTML5/CSS3 (Modern design system)
- Vanilla JavaScript
- Responsive Grid/Flexbox
- CSS Variables for theming

**AI Integration:**
- Spring AI Chat Client API
- Google Gemini 2.0 Flash
- Message Chat Memory Advisor
- Database-driven knowledge base

**AI Integration:**
- Spring AI Chat Client API
- Google Gemini 2.0 Flash
- Message Chat Memory Advisor
- Database-driven knowledge base

### 📋 Prerequisites

- **Java 21+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.8+** (Included wrapper: `./mvnw`)
- **MariaDB/MySQL 5.7+** ([Download MariaDB](https://mariadb.org/download/))
- **Google Gemini API Key** ([Get Free Key](https://aistudio.google.com/apikey)) - 1,500 requests/day free tier

### 🚀 Installation & Setup

#### 1️⃣ Clone the Repository
```bash
git clone https://github.com/iamnguyenvu/java-www-lab.git
cd java-www-lab\thymeleaf-jpa-shopping
```

#### 2️⃣ Create Database
```sql
CREATE DATABASE shopping CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3️⃣ Configure Database Connection
Create a `.env` file in the project root:
```env
# Database Configuration
DB_URL=jdbc:mariadb://localhost:3306/shopping
DB_USERNAME=root
DB_PASSWORD=your_password

# Server Configuration
SERVER_PORT=8088

# Logging Level
LOGGING_LEVEL=INFO

# Google Gemini API Key (Get from: https://aistudio.google.com/apikey)
GEMINI_API_KEY=your_gemini_api_key_here
```

Or update `src/main/resources/application.properties`:
```properties
spring.datasource.url=${DB_URL:jdbc:mariadb://localhost:3306/shopping}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:your-password}

spring.ai.google.genai.api-key=${GEMINI_API_KEY:your-api-key-here}
```

#### 4️⃣ Import Sample Data
```bash
# For Windows PowerShell
Get-Content scripts/shopping_complete.sql | mysql -u root -p shopping

# For Linux/Mac
mysql -u root -p shopping < scripts/shopping_complete.sql
```

**Sample Data Includes:**
- 4 Categories (Laptop, Tablet, Smartwatch, Accessories)
- 21 Products with images and descriptions
- 2 Users (admin/customer roles)
- 25+ Comments with ratings
- Sample orders

#### 5️⃣ Build & Run

**Option A: Using Maven Wrapper (Recommended)**
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

**Option B: Using Installed Maven**
```bash
mvn clean install
mvn spring-boot:run
```

#### 6️⃣ Access the Application
- **Main App**: http://localhost:8088
- **H2 Console** (if enabled): http://localhost:8088/h2-console

### 👥 Default Users

| Role | Username | Password | Description |
|------|----------|----------|-------------|
| Admin | admin | 1234567 | Full access to all features |
| Customer | nguyenvana | 123456 | Shopping and order management |
| Customer | tranthib | 123456 | Shopping and order management |
| Customer | levanc | 123456 | Shopping and order management |
| Customer | phamthid | 123456 | Shopping and order management |

### 📁 Project Structure

```
thymeleaf-jpa-shopping/
├── src/
│   ├── main/
│   │   ├── java/com/nguyenvu/thymeleafjpashopping/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # MVC Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # JPA Entities
│   │   │   ├── repository/      # Spring Data Repositories
│   │   │   ├── security/        # Security configurations
│   │   │   ├── service/         # Business logic
│   │   │   ├── util/            # Utility classes
│   │   │   └── validation/      # Custom validators
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/         # Stylesheets
│   │       │   └── images/      # Product images
│   │       └── templates/       # Thymeleaf templates
│   │           ├── cart/        # Cart & checkout
│   │           ├── customer/    # Customer management
│   │           ├── fragments/   # Reusable fragments
│   │           ├── order/       # Order pages
│   │           └── product/     # Product pages
│   └── test/                    # Unit and integration tests
├── scripts/                     # SQL scripts
├── .env.example                 # Environment variables template
└── pom.xml                      # Maven dependencies
```

### 🎨 UI Design System

**Color Palette (Cyan Theme):**
```css
--primary: #00BCD4        /* Main cyan color */
--primary-dark: #0097A7   /* Darker cyan for hover */
--primary-light: #E0F7FA  /* Light cyan backgrounds */
--accent: #FF5722         /* Orange for CTAs */
--success: #4CAF50        /* Green for success states */
--danger: #F44336         /* Red for errors */
--text: #0F172A           /* Dark text */
--muted: #5F6B7A          /* Gray text */
```

**Components:**
- Modern card design with shadows
- Gradient headers
- Status badges (success/danger/warning/info)
- Responsive tables
- Modern forms with validation
- Floating chatbot widget

### 🧪 Testing

#### Run Tests
```bash
./mvnw test
```

#### Test Chatbot API
```powershell
# Windows PowerShell
.\test-chatbot.ps1

# Manual test
$body = @{
    message = "What products do you have?"
    sessionId = "test-001"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8088/api/chat" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body
```

### 📚 API Endpoints

#### Public Endpoints
- `GET /` - Home page (redirects to products)
- `GET /products` - Product list
- `GET /products/{id}` - Product detail
- `GET /cart` - View cart
- `POST /cart/add/{id}` - Add to cart
- `POST /api/chat` - Chatbot endpoint

#### Authenticated Endpoints
- `POST /cart/checkout` - Checkout (requires login)
- `GET /orders` - View orders (Customer/Admin)
- `GET /orders/{id}` - Order detail (Customer/Admin)

#### Admin Endpoints
- `GET /customers` - Customer management
- `GET /products/new` - Create product form
- `POST /products/new` - Create product
- `GET /products/edit/{id}` - Edit product form
- `POST /products/edit/{id}` - Update product
- `POST /products/delete` - Delete product

### 🐛 Troubleshooting

#### Port Already in Use
```bash
# Windows
netstat -ano | findstr :8088
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8088 | xargs kill -9
```

#### Database Connection Error
- Verify MariaDB is running
- Check credentials in `.env`
- Ensure database `shopping` exists

#### Chatbot Not Working
- Verify `GEMINI_API_KEY` in `.env`
- Check logs for API errors
- Ensure `/api/chat` CSRF exception is configured

### 📖 Documentation

- [UI Changes](UI_CHANGES.md) - Complete UI upgrade documentation
- [Chatbot Fix](CHATBOT_FIX.md) - Chatbot troubleshooting guide
- [Complete Summary](COMPLETE_SUMMARY.md) - Full project summary

### 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### 📧 Contact

**Author:** Nguyen Vu

- GitHub: [@iamnguyenvu](https://github.com/iamnguyenvu)
- Email: iamnguyenvu.gm@gmail.com

### 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### 🙏 Acknowledgments

- Spring Boot Team for the amazing framework
- Google for Gemini AI API
- MariaDB Foundation
- Thymeleaf Community

---

<a name="tiếng-việt"></a>
## 📖 Tài liệu Tiếng Việt

### 🌟 Tổng quan

Nền tảng thương mại điện tử đầy đủ tính năng được xây dựng bằng **Spring Boot**, **Thymeleaf**, **JPA/Hibernate**, và **Spring AI**. Giao diện hiện đại với màu cyan (#00BCD4), responsive design, và chatbot thông minh được hỗ trợ bởi Google Gemini AI.

### ✨ Tính năng chính

#### 👤 Tính năng Khách hàng
- **Xác thực người dùng**: Đăng ký/Đăng nhập với validation đầy đủ
- **Duyệt sản phẩm**: Xem theo danh mục, tìm kiếm, lọc theo giá
- **Giao diện hiện đại**: Card sản phẩm với hình ảnh, gradient, badges
- **Giỏ hàng thông minh**: Thêm/xóa/cập nhật với kiểm tra tồn kho tự động
- **Thanh toán**: Đặt hàng với validation địa chỉ và số điện thoại
- **Theo dõi đơn hàng**: Xem lịch sử với thông tin chi tiết
- **AI Chatbot**: Widget nổi trên mọi trang với gợi ý sản phẩm realtime

#### 🔐 Tính năng Admin
- **Quản lý sản phẩm**: CRUD đầy đủ với validation
- **Quản lý khách hàng**: Xem danh sách và thông tin chi tiết
- **Quản lý đơn hàng**: Xem tất cả và cập nhật trạng thái
- **Dashboard**: Thống kê và phân tích (sẵn sàng mở rộng)
- **Phân quyền**: Endpoints bảo mật với Spring Security

#### 🤖 AI Chatbot (Google Gemini 2.0 Flash)
- 💬 **Widget Nổi**: Góc phải dưới trên mọi trang
- 🇻🇳 **Hỗ trợ Tiếng Việt**: Bản địa hóa hoàn toàn
- 🎯 **Gợi ý Thông minh**: Dựa trên dữ liệu sản phẩm thực
- 📊 **Thông tin Chính xác**: Tên, giá, tồn kho, danh mục
- 💾 **Lịch sử Chat**: Bộ nhớ hội thoại theo session
- 📱 **Responsive**: Tối ưu cho mobile và desktop
- ✨ **UI Hiện đại**: Typing indicator, animations mượt

### 🛠️ Công nghệ sử dụng

**Backend:**
- Spring Boot 3.5.6
- Spring Security (Xác thực dựa trên vai trò)
- Spring Data JPA với Hibernate
- Spring AI 1.1.0-RC1 (Tích hợp Google GenAI)
- MariaDB 11.6.2
- Maven
- Lombok

**Frontend:**
- Thymeleaf 3.1.3
- HTML5/CSS3 (Hệ thống thiết kế hiện đại)
- Vanilla JavaScript
- Responsive Grid/Flexbox
- CSS Variables cho theming

**Tích hợp AI:**
- Spring AI Chat Client API
- Google Gemini 2.0 Flash
- Message Chat Memory Advisor
- Knowledge base từ database

### 📋 Yêu cầu hệ thống

- **Java 21+** ([Tải xuống](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.8+** (Bao gồm wrapper: `./mvnw`)
- **MariaDB/MySQL 5.7+** ([Tải MariaDB](https://mariadb.org/download/))
- **Google Gemini API Key** ([Lấy Key Miễn phí](https://aistudio.google.com/apikey)) - 1,500 requests/ngày

### 🚀 Cài đặt & Thiết lập

#### 1️⃣ Clone Repository
```bash
git clone https://github.com/iamnguyenvu/thymeleaf-jpa-shopping.git
cd thymeleaf-jpa-shopping
```

#### 2️⃣ Tạo Database
```sql
CREATE DATABASE shopping CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3️⃣ Cấu hình Kết nối Database
Tạo file `.env` trong thư mục gốc:
```env
# Cấu hình Database
DB_URL=jdbc:mariadb://localhost:3306/shopping
DB_USERNAME=root
DB_PASSWORD=your_password

# Cấu hình Server
SERVER_PORT=8088

# Mức độ Logging
LOGGING_LEVEL=INFO

# Google Gemini API Key (Lấy từ: https://aistudio.google.com/apikey)
GEMINI_API_KEY=your_gemini_api_key_here
```

Hoặc cập nhật `src/main/resources/application.properties`:
```properties
spring.datasource.url=${DB_URL:jdbc:mariadb://localhost:3306/shopping}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:rootpassword}

spring.ai.google.genai.api-key=${GEMINI_API_KEY:your-api-key-here}
```

#### 4️⃣ Import Dữ liệu Mẫu
```bash
# Cho Windows PowerShell
Get-Content scripts/shopping_complete.sql | mysql -u root -p shopping

# Cho Linux/Mac
mysql -u root -p shopping < scripts/shopping_complete.sql
```

**Dữ liệu mẫu bao gồm:**
- 4 Danh mục (Laptop, Tablet, Smartwatch, Accessories)
- 21 Sản phẩm với hình ảnh và mô tả
- 2 Người dùng (vai trò admin/customer)
- 25+ Bình luận với đánh giá
- Đơn hàng mẫu

#### 5️⃣ Build & Chạy

**Phương án A: Sử dụng Maven Wrapper (Khuyến nghị)**
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

**Phương án B: Sử dụng Maven đã cài**
```bash
mvn clean install
mvn spring-boot:run
```

#### 6️⃣ Truy cập Ứng dụng
- **Ứng dụng chính**: http://localhost:8088
- **H2 Console** (nếu bật): http://localhost:8088/h2-console

### 👥 Tài khoản Mặc định

| Vai trò | Tên đăng nhập | Mật khẩu | Mô tả |
|---------|---------------|----------|-------|
| Admin | admin | admin123 | Quyền truy cập đầy đủ |
| Khách hàng | customer | customer123 | Mua sắm và quản lý đơn hàng |

### 📁 Cấu trúc Dự án

```
thymeleaf-jpa-shopping/
├── src/
│   ├── main/
│   │   ├── java/com/nguyenvu/thymeleafjpashopping/
│   │   │   ├── config/          # Các class cấu hình
│   │   │   ├── controller/      # MVC Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # JPA Entities
│   │   │   ├── repository/      # Spring Data Repositories
│   │   │   ├── security/        # Cấu hình bảo mật
│   │   │   ├── service/         # Logic nghiệp vụ
│   │   │   ├── util/            # Các class tiện ích
│   │   │   └── validation/      # Validators tùy chỉnh
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/         # Stylesheets
│   │       │   └── images/      # Hình ảnh sản phẩm
│   │       └── templates/       # Thymeleaf templates
│   │           ├── cart/        # Giỏ hàng & thanh toán
│   │           ├── customer/    # Quản lý khách hàng
│   │           ├── fragments/   # Fragments tái sử dụng
│   │           ├── order/       # Trang đơn hàng
│   │           └── product/     # Trang sản phẩm
│   └── test/                    # Unit và integration tests
├── scripts/                     # SQL scripts
├── .env.example                 # Template biến môi trường
└── pom.xml                      # Maven dependencies
```

### 🎨 Hệ thống Thiết kế UI

**Bảng màu (Cyan Theme):**
```css
--primary: #00BCD4        /* Màu cyan chính */
--primary-dark: #0097A7   /* Cyan đậm cho hover */
--primary-light: #E0F7FA  /* Cyan nhạt cho nền */
--accent: #FF5722         /* Cam cho CTAs */
--success: #4CAF50        /* Xanh lá cho thành công */
--danger: #F44336         /* Đỏ cho lỗi */
--text: #0F172A           /* Chữ tối */
--muted: #5F6B7A          /* Chữ xám */
```

**Components:**
- Thiết kế card hiện đại với shadows
- Gradient headers
- Status badges (success/danger/warning/info)
- Responsive tables
- Forms hiện đại với validation
- Floating chatbot widget

### 🧪 Testing

#### Chạy Tests
```bash
./mvnw test
```

#### Test Chatbot API
```powershell
# Windows PowerShell
.\test-chatbot.ps1

# Test thủ công
$body = @{
    message = "Có sản phẩm gì giá rẻ không?"
    sessionId = "test-001"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8088/api/chat" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body
```

### 📚 API Endpoints

#### Endpoints Công khai
- `GET /` - Trang chủ (chuyển hướng đến products)
- `GET /products` - Danh sách sản phẩm
- `GET /products/{id}` - Chi tiết sản phẩm
- `GET /cart` - Xem giỏ hàng
- `POST /cart/add/{id}` - Thêm vào giỏ
- `POST /api/chat` - Chatbot endpoint

#### Endpoints Yêu cầu Đăng nhập
- `POST /cart/checkout` - Thanh toán
- `GET /orders` - Xem đơn hàng (Customer/Admin)
- `GET /orders/{id}` - Chi tiết đơn hàng (Customer/Admin)

#### Endpoints Admin
- `GET /customers` - Quản lý khách hàng
- `GET /products/new` - Form tạo sản phẩm
- `POST /products/new` - Tạo sản phẩm
- `GET /products/edit/{id}` - Form sửa sản phẩm
- `POST /products/edit/{id}` - Cập nhật sản phẩm
- `POST /products/delete` - Xóa sản phẩm

### 🐛 Khắc phục Sự cố

#### Port đã được sử dụng
```bash
# Windows
netstat -ano | findstr :8088
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8088 | xargs kill -9
```

#### Lỗi kết nối Database
- Kiểm tra MariaDB đang chạy
- Kiểm tra credentials trong `.env`
- Đảm bảo database `shopping` tồn tại

#### Chatbot không hoạt động
- Kiểm tra `GEMINI_API_KEY` trong `.env`
- Xem logs để tìm lỗi API
- Đảm bảo CSRF exception cho `/api/chat` đã được cấu hình

### 📖 Tài liệu

- [UI Changes](UI_CHANGES.md) - Tài liệu nâng cấp UI hoàn chỉnh
- [Chatbot Fix](CHATBOT_FIX.md) - Hướng dẫn khắc phục chatbot
- [Complete Summary](COMPLETE_SUMMARY.md) - Tổng kết dự án đầy đủ

### 🤝 Đóng góp

Đóng góp luôn được chào đón! Hãy thoải mái tạo Pull Request.

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/TinhNangTuyetVoi`)
3. Commit thay đổi (`git commit -m 'Thêm tính năng tuyệt vời'`)
4. Push lên branch (`git push origin feature/TinhNangTuyetVoi`)
5. Mở Pull Request

### 📧 Liên hệ

**Tác giả:** Nguyen Vu

- GitHub: [@iamnguyenvu](https://github.com/iamnguyenvu)
- Email: iamnguyenvu.gm@gmail.com

### 📄 Giấy phép

Dự án này được cấp phép theo giấy phép MIT - xem file [LICENSE](LICENSE) để biết thêm chi tiết.

### 🙏 Cảm ơn

- Spring Boot Team vì framework tuyệt vời
- Google vì Gemini AI API
- MariaDB Foundation
- Thymeleaf Community

---

<div align="center">

**⭐ Nếu dự án hữu ích, hãy cho một ngôi sao! ⭐**

Made with ❤️ by [Nguyen Vu](https://github.com/iamnguyenvu)

</div>

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
