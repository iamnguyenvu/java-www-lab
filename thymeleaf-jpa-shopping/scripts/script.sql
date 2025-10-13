-- Tao database
CREATE DATABASE IF NOT EXISTS shopping;
USE shopping;

-- Xoa du lieu cu (neu co)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE order_lines;
TRUNCATE TABLE orders;
TRUNCATE TABLE comment;
TRUNCATE TABLE products;
TRUNCATE TABLE customers;
SET FOREIGN_KEY_CHECKS = 1;

-- ========================================
-- INSERT DU LIEU MAU
-- ========================================

-- 1. CUSTOMERS - Khach hang
INSERT INTO customers (name, customer_since) VALUES
('Nguyen Van A', '2023-01-15 10:00:00'),
('Tran Thi B', '2023-03-20 14:30:00'),
('Le Van C', '2023-06-10 09:15:00'),
('Pham Thi D', '2023-08-05 16:45:00'),
('Hoang Van E', '2024-01-01 08:00:00'),
('Vu Thi F', '2024-03-15 11:20:00'),
('Do Van G', '2024-06-20 13:30:00'),
('Bui Thi H', '2024-09-01 15:10:00');

-- 2. PRODUCTS - San pham
INSERT INTO products (name, price, in_stock) VALUES
('Laptop Dell XPS 13', 25000000.00, true),
('iPhone 15 Pro Max', 32000000.00, true),
('Samsung Galaxy S24', 22000000.00, true),
('MacBook Air M2', 28000000.00, true),
('iPad Pro 12.9', 18000000.00, true),
('Apple Watch Series 9', 12000000.00, true),
('Sony WH-1000XM5', 8500000.00, true),
('AirPods Pro 2', 6500000.00, true),
('Magic Mouse', 2500000.00, true),
('Samsung 4K Monitor', 7500000.00, true),
('Logitech MX Master 3', 2200000.00, false),
('Keychron K8 Pro', 3500000.00, true),
('Dell UltraSharp 27', 8900000.00, true),
('Canon EOS R6', 45000000.00, false),
('Sony A7 IV', 52000000.00, true);

-- 3. COMMENTS - Binh luan san pham
INSERT INTO comment (text, product_id) VALUES
('San pham rat tot, giao hang nhanh!', 1),
('Chat luong xuat sac, dang tien!', 1),
('May chay muot, thiet ke dep', 1),
('Camera rat net, pin tot', 2),
('Gia hoi cao nhung chac chat', 2),
('Man hinh dep, hieu nang manh', 3),
('Pin kha on, su dung ca ngay', 3),
('Nhe, manh, pin trau', 4),
('Tan nhiet tot, khong on', 4),
('Man hinh lon, ve dep tuyet voi', 5),
('Am thanh trong, chong on tot', 7),
('Deo thoai mai, pin lau', 7),
('Ket noi nhanh, am thanh chat', 8),
('Thiet ke nho gon, de su dung', 9),
('Man hinh sac net, mau sac chuan', 10),
('Ban phim go nhe nhang', 12),
('Anh sac net, mau dep', 14),
('Chat luong chuyen nghiep', 15);

-- 4. ORDERS - Don hang
INSERT INTO orders (date, customer_id) VALUES
('2024-01-15 10:30:00', 1),
('2024-02-20 14:15:00', 1),
('2024-03-10 09:45:00', 2),
('2024-04-05 16:20:00', 2),
('2024-05-12 11:00:00', 3),
('2024-06-18 13:30:00', 4),
('2024-07-22 15:45:00', 5),
('2024-08-08 10:15:00', 5),
('2024-09-14 12:00:00', 6),
('2024-09-25 14:30:00', 7),
('2024-10-01 09:00:00', 8),
('2024-10-05 16:45:00', 8),
('2024-10-10 11:20:00', 3),
('2024-10-12 13:50:00', 6);

-- 5. ORDER_LINES - Chi tiet don hang
-- Don hang 1: Khach A mua Laptop + Mouse
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 25000000.00, 1, 1),
(1, 2500000.00, 9, 1);

-- Don hang 2: Khach A mua iPhone + AirPods
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 32000000.00, 2, 2),
(2, 6500000.00, 8, 2);

-- Don hang 3: Khach B mua Samsung + Watch
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 22000000.00, 3, 3),
(1, 12000000.00, 6, 3);

-- Don hang 4: Khach B mua Headphone + Keyboard
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 8500000.00, 7, 4),
(1, 3500000.00, 12, 4);

-- Don hang 5: Khach C mua MacBook + Monitor
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 28000000.00, 4, 5),
(1, 7500000.00, 10, 5);

-- Don hang 6: Khach D mua iPad + Apple Watch
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 18000000.00, 5, 6),
(1, 12000000.00, 6, 6);

-- Don hang 7: Khach E mua Dell Monitor + Mouse
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 8900000.00, 13, 7),
(2, 2500000.00, 9, 7);

-- Don hang 8: Khach E mua Laptop + Keyboard
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 25000000.00, 1, 8),
(1, 3500000.00, 12, 8);

-- Don hang 9: Khach F mua iPhone + AirPods + Watch
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 32000000.00, 2, 9),
(1, 6500000.00, 8, 9),
(1, 12000000.00, 6, 9);

-- Don hang 10: Khach G mua Samsung + Headphone
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 22000000.00, 3, 10),
(1, 8500000.00, 7, 10);

-- Don hang 11: Khach H mua MacBook + iPad
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 28000000.00, 4, 11),
(1, 18000000.00, 5, 11);

-- Don hang 12: Khach H mua Sony Camera
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 52000000.00, 15, 12);

-- Don hang 13: Khach C mua Monitor + Keyboard
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 7500000.00, 10, 13),
(1, 3500000.00, 12, 13);

-- Don hang 14: Khach F mua Headphone
INSERT INTO order_lines (amount, purchase_price, product_id, order_id) VALUES
(1, 8500000.00, 7, 14);

-- ========================================
-- KIEM TRA DU LIEU
-- ========================================
SELECT 'Customers:' AS Info, COUNT(*) AS Total FROM customers
UNION ALL
SELECT 'Products:', COUNT(*) FROM products
UNION ALL
SELECT 'Comments:', COUNT(*) FROM comment
UNION ALL
SELECT 'Orders:', COUNT(*) FROM orders
UNION ALL
SELECT 'Order Lines:', COUNT(*) FROM order_lines;