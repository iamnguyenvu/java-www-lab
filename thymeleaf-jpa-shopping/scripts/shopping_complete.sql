-- =============================================
-- SHOPPING DATABASE SCRIPT - MariaDB
-- Drop và tạo lại database từ đầu
-- =============================================

-- Xóa database cũ và tạo mới
DROP DATABASE IF EXISTS shopping;
CREATE DATABASE shopping CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE shopping;

-- =============================================
-- TABLE: category
-- =============================================
CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABLE: customer
-- =============================================
CREATE TABLE customer (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(500),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    customer_since DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABLE: product
-- =============================================
CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    image_url VARCHAR(500),
    in_stock BOOLEAN NOT NULL DEFAULT TRUE,
    category_id BIGINT,
    INDEX idx_name (name),
    INDEX idx_category (category_id),
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) 
        REFERENCES category(category_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABLE: orders (tên bảng là 'orders' vì 'order' là keyword)
-- =============================================
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(500),
    phone VARCHAR(20),
    customer_id BIGINT NOT NULL,
    INDEX idx_order_date (order_date),
    INDEX idx_status (status),
    INDEX idx_customer (customer_id),
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) 
        REFERENCES customer(customer_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABLE: order_line
-- =============================================
CREATE TABLE order_line (
    order_line_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    INDEX idx_order (order_id),
    INDEX idx_product (product_id),
    CONSTRAINT fk_orderline_order FOREIGN KEY (order_id) 
        REFERENCES orders(order_id) ON DELETE CASCADE,
    CONSTRAINT fk_orderline_product FOREIGN KEY (product_id) 
        REFERENCES product(product_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- TABLE: comment
-- =============================================
CREATE TABLE comment (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    comment_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    product_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    INDEX idx_product (product_id),
    INDEX idx_customer (customer_id),
    INDEX idx_rating (rating),
    CONSTRAINT fk_comment_product FOREIGN KEY (product_id) 
        REFERENCES product(product_id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_customer FOREIGN KEY (customer_id) 
        REFERENCES customer(customer_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================
-- INSERT SAMPLE DATA
-- =============================================

-- Insert Categories
INSERT INTO category (name, description) VALUES
('Điện thoại', 'Điện thoại di động các loại'),
('Laptop', 'Máy tính xách tay'),
('Phụ kiện', 'Phụ kiện điện tử'),
('Tablet', 'Máy tính bảng'),
('Đồng hồ thông minh', 'Smart watch và fitness tracker');

-- Insert Customers
-- Password cho tất cả: 123456 (BCrypt encoded) $2a$10$RGRfoRy8tQPDeurB6UB3IOC9RRX9TcbCpIcHcv.z9GYOVgZa9ZbDq
INSERT INTO customer (username, password, name, email, phone, address, role, enabled, customer_since) VALUES
('admin', '$2a$10$RGRfoRy8tQPDeurB6UB3IOC9RRX9TcbCpIcHcv.z9GYOVgZa9ZbDq', 'Admin User', 'admin@shopping.com', '0901234567', '123 Admin Street, HCMC', 'ADMIN', TRUE, '2024-01-01 00:00:00'),
('nguyenvana', '$2a$10$RGRfoRy8tQPDeurB6UB3IOC9RRX9TcbCpIcHcv.z9GYOVgZa9ZbDq', 'Nguyễn Văn A', 'nguyenvana@email.com', '0912345678', '456 Nguyen Hue, HCMC', 'CUSTOMER', TRUE, '2024-06-15 10:30:00'),
('tranthib', '$2a$10$RGRfoRy8tQPDeurB6UB3IOC9RRX9TcbCpIcHcv.z9GYOVgZa9ZbDq', 'Trần Thị B', 'tranthib@email.com', '0923456789', '789 Le Loi, HCMC', 'CUSTOMER', TRUE, '2024-07-20 14:45:00'),
('levanc', '$2a$10$RGRfoRy8tQPDeurB6UB3IOC9RRX9TcbCpIcHcv.z9GYOVgZa9ZbDq', 'Lê Văn C', 'levanc@email.com', '0934567890', '321 Tran Hung Dao, Hanoi', 'CUSTOMER', TRUE, '2024-08-10 09:15:00'),
('phamthid', '$2a$10$RGRfoRy8tQPDeurB6UB3IOC9RRX9TcbCpIcHcv.z9GYOVgZa9ZbDq', 'Phạm Thị D', 'phamthid@email.com', '0945678901', '654 Hai Ba Trung, Danang', 'CUSTOMER', TRUE, '2024-09-05 16:20:00');

-- Insert Products
INSERT INTO product (name, description, price, stock, image_url, in_stock, category_id) VALUES
-- Điện thoại
('iPhone 15 Pro Max', 'Apple iPhone 15 Pro Max 256GB - Titan Tự Nhiên', 29990000, 50, '/images/iphone15promax.jpg', TRUE, 1),
('Samsung Galaxy S24 Ultra', 'Samsung Galaxy S24 Ultra 12GB/256GB - Đen Titan', 27990000, 45, '/images/s24ultra.jpg', TRUE, 1),
('Xiaomi 14 Pro', 'Xiaomi 14 Pro 12GB/256GB - Đen Titan', 19990000, 60, '/images/xiaomi14pro.jpg', TRUE, 1),
('OPPO Find X7 Ultra', 'OPPO Find X7 Ultra 16GB/512GB - Nâu Sa Mạc', 23990000, 30, '/images/oppofindx7.jpg', TRUE, 1),
('Vivo X100 Pro', 'Vivo X100 Pro 16GB/512GB - Xanh Thiên Thạch', 21990000, 25, '/images/vivox100pro.jpg', TRUE, 1),

-- Laptop
('MacBook Pro 16 M3', 'Apple MacBook Pro 16 inch M3 Pro 36GB/512GB', 69990000, 20, '/images/macbookpro16.jpg', TRUE, 2),
('Dell XPS 15', 'Dell XPS 15 9530 i9-13900H/32GB/1TB/RTX4070', 55990000, 15, '/images/dellxps15.jpg', TRUE, 2),
('ASUS ROG Zephyrus G16', 'ASUS ROG Zephyrus G16 i9-13900H/32GB/1TB/RTX4080', 62990000, 12, '/images/rogg16.jpg', TRUE, 2),
('Lenovo ThinkPad X1 Carbon', 'Lenovo ThinkPad X1 Carbon Gen 11 i7-1355U/16GB/512GB', 42990000, 18, '/images/thinkpadx1.jpg', TRUE, 2),
('HP Spectre x360', 'HP Spectre x360 14 i7-1355U/16GB/1TB OLED Touch', 45990000, 10, '/images/hpspectre.jpg', TRUE, 2),

-- Phụ kiện
('AirPods Pro 2', 'Apple AirPods Pro 2nd Generation USB-C', 5990000, 100, '/images/airpodspro2.jpg', TRUE, 3),
('Samsung Buds2 Pro', 'Samsung Galaxy Buds2 Pro - Graphite', 3990000, 80, '/images/buds2pro.jpg', TRUE, 3),
('Magic Mouse', 'Apple Magic Mouse Black Multi-Touch Surface', 2290000, 50, '/images/magicmouse.jpg', TRUE, 3),
('Logitech MX Master 3S', 'Logitech MX Master 3S Wireless Mouse', 2490000, 60, '/images/mxmaster3s.jpg', TRUE, 3),
('Anker PowerBank 20000mAh', 'Anker PowerCore III Elite 20000mAh 65W PD', 1790000, 120, '/images/ankerpowerbank.jpg', TRUE, 3),

-- Tablet
('iPad Pro 12.9 M2', 'Apple iPad Pro 12.9 inch M2 WiFi 256GB', 29990000, 35, '/images/ipadpro129.jpg', TRUE, 4),
('Samsung Tab S9 Ultra', 'Samsung Galaxy Tab S9 Ultra 12GB/256GB WiFi', 26990000, 28, '/images/tabs9ultra.jpg', TRUE, 4),
('iPad Air M1', 'Apple iPad Air 10.9 inch M1 WiFi 256GB', 17990000, 40, '/images/ipadair.jpg', TRUE, 4),

-- Đồng hồ thông minh
('Apple Watch Series 9', 'Apple Watch Series 9 GPS 45mm Aluminum', 10990000, 55, '/images/applewatch9.jpg', TRUE, 5),
('Samsung Galaxy Watch 6', 'Samsung Galaxy Watch 6 44mm LTE', 7990000, 48, '/images/galaxywatch6.jpg', TRUE, 5),
('Garmin Fenix 7X', 'Garmin Fenix 7X Sapphire Solar GPS', 23990000, 15, '/images/fenix7x.jpg', TRUE, 5);

-- Insert Orders cho customers
-- Order 1: nguyenvana - DELIVERED
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) 
VALUES ('2024-09-15 10:30:00', 35980000, 'DELIVERED', '456 Nguyen Hue, HCMC', '0912345678', 2);

INSERT INTO order_line (quantity, unit_price, subtotal, order_id, product_id) VALUES
(1, 29990000, 29990000, 1, 1),  -- iPhone 15 Pro Max
(1, 5990000, 5990000, 1, 11);    -- AirPods Pro 2

-- Order 2: nguyenvana - SHIPPING
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) 
VALUES ('2024-10-20 14:20:00', 72280000, 'SHIPPING', '456 Nguyen Hue, HCMC', '0912345678', 2);

INSERT INTO order_line (quantity, unit_price, subtotal, order_id, product_id) VALUES
(1, 69990000, 69990000, 2, 6),  -- MacBook Pro 16 M3
(1, 2290000, 2290000, 2, 13);    -- Magic Mouse

-- Order 3: tranthib - DELIVERED
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) 
VALUES ('2024-08-25 09:45:00', 23990000, 'DELIVERED', '789 Le Loi, HCMC', '0923456789', 3);

INSERT INTO order_line (quantity, unit_price, subtotal, order_id, product_id) VALUES
(1, 19990000, 19990000, 3, 3),  -- Xiaomi 14 Pro
(1, 3990000, 3990000, 3, 12);    -- Samsung Buds2 Pro

-- Order 4: tranthib - CONFIRMED
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) 
VALUES ('2024-10-28 16:30:00', 29980000, 'CONFIRMED', '789 Le Loi, HCMC', '0923456789', 3);

INSERT INTO order_line (quantity, unit_price, subtotal, order_id, product_id) VALUES
(1, 29990000, 29990000, 4, 16);  -- iPad Pro 12.9 M2

-- Order 5: levanc - PENDING
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) 
VALUES ('2024-10-29 11:00:00', 65980000, 'PENDING', '321 Tran Hung Dao, Hanoi', '0934567890', 4);

INSERT INTO order_line (quantity, unit_price, subtotal, order_id, product_id) VALUES
(1, 55990000, 55990000, 5, 7),   -- Dell XPS 15
(1, 10990000, 10990000, 5, 19);  -- Apple Watch Series 9

-- Order 6: phamthid - DELIVERED
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) 
VALUES ('2024-09-10 13:15:00', 27990000, 'DELIVERED', '654 Hai Ba Trung, Danang', '0945678901', 5);

INSERT INTO order_line (quantity, unit_price, subtotal, order_id, product_id) VALUES
(1, 27990000, 27990000, 6, 2);   -- Samsung Galaxy S24 Ultra

-- Order 7: phamthid - SHIPPING
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) 
VALUES ('2024-10-25 10:20:00', 21570000, 'SHIPPING', '654 Hai Ba Trung, Danang', '0945678901', 5);

INSERT INTO order_line (quantity, unit_price, subtotal, order_id, product_id) VALUES
(1, 17990000, 17990000, 7, 18),  -- iPad Air M1
(2, 1790000, 3580000, 7, 15);    -- Anker PowerBank x2

-- Insert Comments/Reviews
INSERT INTO comment (content, comment_date, rating, product_id, customer_id) VALUES
('iPhone 15 Pro Max rất đáng tiền! Camera xuất sắc, hiệu năng mạnh mẽ.', '2024-09-20 10:00:00', 5, 1, 2),
('MacBook Pro M3 quá đỉnh! Màn hình đẹp, hiệu năng vượt trội.', '2024-10-22 14:30:00', 5, 6, 2),
('Xiaomi 14 Pro giá tốt, hiệu năng ổn, camera chất lượng.', '2024-09-01 09:15:00', 4, 3, 3),
('Galaxy S24 Ultra màn hình đẹp, S Pen tiện lợi. Rất hài lòng!', '2024-09-15 16:45:00', 5, 2, 5),
('AirPods Pro 2 chống ồn tuyệt vời, âm thanh trong trẻo.', '2024-09-21 11:20:00', 5, 11, 2),
('Dell XPS 15 thiết kế đẹp nhưng hơi nóng khi chơi game.', '2024-10-29 15:00:00', 4, 7, 4),
('Samsung Buds2 Pro sound quality tốt, giá hợp lý.', '2024-09-02 13:30:00', 4, 12, 3),
('iPad Pro M2 màn hình siêu đẹp, lý tưởng cho designer.', '2024-10-28 18:00:00', 5, 16, 3),
('iPad Air M1 cân bằng giữa giá và hiệu năng. Đáng mua!', '2024-10-26 10:45:00', 5, 18, 5),
('Apple Watch 9 tracking sức khỏe chính xác, pin tốt.', '2024-10-29 12:30:00', 5, 19, 4);

-- =============================================
-- SUMMARY
-- =============================================
SELECT 'Database created successfully!' AS Status;
SELECT COUNT(*) AS Categories FROM category;
SELECT COUNT(*) AS Customers FROM customer;
SELECT COUNT(*) AS Products FROM product;
SELECT COUNT(*) AS Orders FROM orders;
SELECT COUNT(*) AS OrderLines FROM order_line;
SELECT COUNT(*) AS Comments FROM comment;
