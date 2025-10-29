-- ========================================
-- Shopping Database Schema - Updated Version
-- ========================================

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS `shopping` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `shopping`;

-- Drop existing tables in correct order (respecting foreign keys)
DROP TABLE IF EXISTS order_line;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS customer;

-- ========================================
-- TABLE: category
-- ========================================
CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    INDEX idx_category_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLE: customer
-- ========================================
CREATE TABLE customer (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(500),
    customer_since DATETIME NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_customer_username (username),
    INDEX idx_customer_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLE: product
-- ========================================
CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    price DECIMAL(10,2) NOT NULL,
    in_stock BOOLEAN NOT NULL DEFAULT TRUE,
    stock INT NOT NULL DEFAULT 0,
    image_url VARCHAR(500),
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE RESTRICT,
    INDEX idx_product_name (name),
    INDEX idx_product_category (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLE: comment
-- ========================================
CREATE TABLE comment (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(1000) NOT NULL,
    comment_date DATETIME NOT NULL,
    rating INT NOT NULL DEFAULT 5,
    product_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
    INDEX idx_comment_product (product_id),
    INDEX idx_comment_customer (customer_id),
    INDEX idx_comment_date (comment_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLE: orders
-- ========================================
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(500),
    phone VARCHAR(20),
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE RESTRICT,
    INDEX idx_order_customer (customer_id),
    INDEX idx_order_date (order_date),
    INDEX idx_order_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLE: order_line
-- ========================================
CREATE TABLE order_line (
    order_line_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    product_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE RESTRICT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    INDEX idx_orderline_order (order_id),
    INDEX idx_orderline_product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- SAMPLE DATA: category
-- ========================================
INSERT INTO category (name, description) VALUES
('Electronics', 'Electronic devices, computers, and accessories'),
('Books', 'Books, e-books, and educational materials'),
('Clothing', 'Fashion, apparel, and accessories'),
('Food & Beverages', 'Food products, drinks, and groceries'),
('Home & Garden', 'Home decoration, furniture, and garden tools'),
('Sports & Outdoors', 'Sports equipment, outdoor gear, and fitness'),
('Toys & Games', 'Toys, games, and entertainment for kids'),
('Beauty & Personal Care', 'Cosmetics, skincare, and personal hygiene');

-- ========================================
-- SAMPLE DATA: customer
-- Note: Password is '123456' encoded with BCrypt
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- ========================================
INSERT INTO customer (name, username, password, email, phone, address, customer_since, role, enabled) VALUES
('Administrator', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@shop.com', '0123456789', '123 Admin Street, City Center', NOW(), 'ADMIN', TRUE),
('John Doe', 'john_doe', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'john.doe@email.com', '0987654321', '456 Customer Avenue, District 1', NOW() - INTERVAL 6 MONTH, 'CUSTOMER', TRUE),
('Jane Smith', 'jane_smith', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'jane.smith@email.com', '0912345678', '789 Buyer Boulevard, District 2', NOW() - INTERVAL 4 MONTH, 'CUSTOMER', TRUE),
('Bob Wilson', 'bob_wilson', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'bob.wilson@email.com', '0909123456', '321 Shop Lane, District 3', NOW() - INTERVAL 3 MONTH, 'CUSTOMER', TRUE),
('Alice Brown', 'alice_brown', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'alice.brown@email.com', '0898765432', '654 Market Street, District 4', NOW() - INTERVAL 2 MONTH, 'CUSTOMER', TRUE),
('Charlie Davis', 'charlie_davis', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'charlie.davis@email.com', '0876543210', '987 Commerce Road, District 5', NOW() - INTERVAL 1 MONTH, 'CUSTOMER', TRUE);

-- ========================================
-- SAMPLE DATA: product
-- ========================================
INSERT INTO product (name, description, price, in_stock, stock, image_url, category_id) VALUES
-- Electronics (category_id = 1)
('Laptop Dell XPS 15', 'High-performance laptop with Intel Core i7 12th Gen, 16GB RAM, 512GB NVMe SSD, NVIDIA RTX 3050, 15.6" FHD display', 1299.99, TRUE, 15, '/images/products/laptop-dell-xps15.jpg', 1),
('iPhone 14 Pro Max', 'Apple iPhone 14 Pro Max 256GB, A16 Bionic chip, ProMotion 120Hz display, 48MP camera system, Deep Purple', 1199.99, TRUE, 25, '/images/products/iphone-14-pro.jpg', 1),
('Samsung Galaxy S23 Ultra', 'Samsung flagship phone with Snapdragon 8 Gen 2, 12GB RAM, 256GB storage, 200MP camera, S Pen included', 999.99, TRUE, 20, '/images/products/samsung-s23-ultra.jpg', 1),
('Sony WH-1000XM5', 'Premium wireless noise-canceling headphones with 30-hour battery life, multipoint connection, superior sound quality', 349.99, TRUE, 30, '/images/products/sony-wh1000xm5.jpg', 1),
('iPad Air 5th Gen', 'Apple iPad Air with M1 chip, 10.9" Liquid Retina display, 64GB WiFi, supports Apple Pencil 2nd gen', 599.99, TRUE, 18, '/images/products/ipad-air-5.jpg', 1),
('Gaming Mouse Logitech G502', 'RGB gaming mouse with HERO 25K sensor, 16000 DPI, 11 programmable buttons, adjustable weights', 79.99, TRUE, 45, '/images/products/logitech-g502.jpg', 1),
('Mechanical Keyboard Corsair K70', 'RGB mechanical gaming keyboard with Cherry MX Red switches, aluminum frame, dedicated media controls', 149.99, TRUE, 35, '/images/products/corsair-k70.jpg', 1),
('Samsung 55" 4K Smart TV', '55-inch QLED 4K Smart TV with Quantum Processor, HDR10+, gaming mode, built-in Alexa', 799.99, TRUE, 12, '/images/products/samsung-tv-55.jpg', 1),

-- Books (category_id = 2)
('Clean Code', 'A Handbook of Agile Software Craftsmanship by Robert C. Martin - Essential reading for software developers', 39.99, TRUE, 50, '/images/products/clean-code.jpg', 2),
('Design Patterns', 'Elements of Reusable Object-Oriented Software by Gang of Four - The classic software design book', 49.99, TRUE, 35, '/images/products/design-patterns.jpg', 2),
('The Pragmatic Programmer', 'Your Journey To Mastery, 20th Anniversary Edition - Timeless wisdom for software development', 44.99, TRUE, 40, '/images/products/pragmatic-programmer.jpg', 2),
('Atomic Habits', 'An Easy & Proven Way to Build Good Habits & Break Bad Ones by James Clear - Life-changing book', 24.99, TRUE, 60, '/images/products/atomic-habits.jpg', 2),

-- Clothing (category_id = 3)
('Men Cotton T-Shirt', 'Premium 100% cotton comfortable t-shirt, multiple colors available (Black, White, Navy, Gray), sizes S-XXL', 19.99, TRUE, 100, '/images/products/mens-tshirt.jpg', 3),
('Women Summer Dress', 'Elegant floral summer dress, lightweight breathable fabric, perfect for any occasion, sizes XS-XL', 59.99, TRUE, 45, '/images/products/womens-dress.jpg', 3),
('Men Jeans Slim Fit', 'Classic blue denim jeans, slim fit style, comfortable stretch fabric, sizes 28-38', 49.99, TRUE, 55, '/images/products/mens-jeans.jpg', 3),
('Women Leather Jacket', 'Genuine leather jacket, modern design, inner lining, multiple pockets, sizes S-L', 149.99, TRUE, 25, '/images/products/womens-jacket.jpg', 3),

-- Food & Beverages (category_id = 4)
('Premium Arabica Coffee Beans', 'Single-origin Arabica coffee beans from Colombia, medium roast, 1kg pack, freshly roasted', 24.99, TRUE, 80, '/images/products/coffee-beans.jpg', 4),
('Organic Green Tea', 'Premium organic green tea leaves, 100 tea bags, rich in antioxidants, Japanese quality', 15.99, TRUE, 65, '/images/products/green-tea.jpg', 4),
('Extra Virgin Olive Oil', 'Cold-pressed extra virgin olive oil from Italy, 1 liter glass bottle, ideal for cooking and salads', 29.99, TRUE, 40, '/images/products/olive-oil.jpg', 4),
('Dark Chocolate 85%', 'Premium dark chocolate bars, 85% cocoa, sugar-free, pack of 5 bars (100g each)', 19.99, TRUE, 70, '/images/products/dark-chocolate.jpg', 4),

-- Home & Garden (category_id = 5)
('LED Desk Lamp', 'Adjustable LED desk lamp with touch control, 5 brightness levels, USB charging port, eye-care technology', 39.99, TRUE, 45, '/images/products/led-desk-lamp.jpg', 5),
('Indoor Plant Pot Set', 'Set of 3 ceramic plant pots with drainage holes, includes saucers, modern minimalist design', 34.99, TRUE, 50, '/images/products/plant-pot-set.jpg', 5),
('Memory Foam Pillow', 'Ergonomic memory foam pillow, hypoallergenic, removable washable cover, neck pain relief', 44.99, TRUE, 38, '/images/products/memory-foam-pillow.jpg', 5),

-- Sports & Outdoors (category_id = 6)
('Running Shoes Nike Air Zoom', 'Professional running shoes with Air Zoom cushioning, breathable mesh upper, sizes 6-13', 119.99, TRUE, 55, '/images/products/nike-running-shoes.jpg', 6),
('Yoga Mat Premium', 'Non-slip yoga mat, 6mm thickness, eco-friendly TPE material, includes carrying strap, 183x61cm', 29.99, TRUE, 75, '/images/products/yoga-mat.jpg', 6),
('Dumbbell Set Adjustable', 'Adjustable dumbbell set, 2.5kg to 25kg per dumbbell, space-saving design, includes stand', 299.99, TRUE, 20, '/images/products/dumbbell-set.jpg', 6),
('Camping Tent 4-Person', 'Waterproof 4-person camping tent, easy setup, UV protection, includes carrying bag and stakes', 149.99, TRUE, 15, '/images/products/camping-tent.jpg', 6),

-- Toys & Games (category_id = 7)
('LEGO Star Wars Set', 'LEGO Star Wars Millennium Falcon building set, 1351 pieces, includes minifigures, age 9+', 159.99, TRUE, 22, '/images/products/lego-starwars.jpg', 7),
('Board Game Catan', 'The Settlers of Catan strategy board game, 3-4 players, ages 10+, award-winning game', 44.99, TRUE, 35, '/images/products/board-game-catan.jpg', 7),
('Remote Control Car', 'High-speed RC car, 1:16 scale, 4WD off-road vehicle, rechargeable battery, 2.4GHz controller', 69.99, TRUE, 28, '/images/products/rc-car.jpg', 7),

-- Beauty & Personal Care (category_id = 8)
('Facial Cleanser Set', 'Complete facial cleansing set with foam cleanser, toner, and moisturizer, suitable for all skin types', 39.99, TRUE, 60, '/images/products/facial-cleanser-set.jpg', 8),
('Electric Toothbrush', 'Sonic electric toothbrush with 5 modes, 2-minute timer, USB charging, includes 4 brush heads', 49.99, TRUE, 42, '/images/products/electric-toothbrush.jpg', 8),
('Hair Dryer Professional', 'Professional ionic hair dryer, 2000W, 3 heat settings, cool shot button, concentrator nozzle', 79.99, TRUE, 30, '/images/products/hair-dryer.jpg', 8);

-- ========================================
-- SAMPLE DATA: comment
-- ========================================
INSERT INTO comment (text, comment_date, rating, product_id, customer_id) VALUES
-- Comments for Electronics
('Amazing laptop! Fast and reliable for programming. The display is crystal clear and keyboard is comfortable.', NOW() - INTERVAL 5 DAY, 5, 1, 2),
('Best phone I ever had. The camera quality is outstanding and battery lasts all day!', NOW() - INTERVAL 8 DAY, 5, 2, 3),
('Good value for money. Performance is great, though the battery could be better.', NOW() - INTERVAL 3 DAY, 4, 3, 4),
('Excellent noise cancellation! Perfect for working from home and traveling.', NOW() - INTERVAL 10 DAY, 5, 4, 5),
('Great tablet for work and entertainment. M1 chip is really powerful.', NOW() - INTERVAL 2 DAY, 5, 5, 6),
('The laptop is good but runs a bit hot when gaming. Otherwise perfect!', NOW() - INTERVAL 1 DAY, 4, 1, 4),
('Superb sound quality and very comfortable for long listening sessions.', NOW() - INTERVAL 6 DAY, 5, 4, 2),

-- Comments for Books
('Must-read for every developer! Changed the way I write code.', NOW() - INTERVAL 15 DAY, 5, 9, 2),
('Classic book with timeless wisdom. Every software engineer should read this.', NOW() - INTERVAL 12 DAY, 5, 10, 3),
('Practical advice that you can apply immediately. Highly recommended!', NOW() - INTERVAL 7 DAY, 5, 11, 5),
('Life-changing book! Helped me build better habits and break bad ones.', NOW() - INTERVAL 4 DAY, 5, 12, 4),

-- Comments for Clothing
('Very comfortable and good quality fabric. Perfect fit!', NOW() - INTERVAL 9 DAY, 4, 13, 3),
('Beautiful dress! Fits perfectly and the fabric is very soft.', NOW() - INTERVAL 11 DAY, 5, 14, 5),
('Great jeans, comfortable stretch. True to size.', NOW() - INTERVAL 5 DAY, 4, 15, 6),
('Quality leather and stylish design. Worth the price!', NOW() - INTERVAL 3 DAY, 5, 16, 2),

-- Comments for Food & Beverages
('Best coffee beans ever! Rich aroma and smooth taste.', NOW() - INTERVAL 6 DAY, 5, 17, 5),
('Great tea quality! Very refreshing and healthy.', NOW() - INTERVAL 8 DAY, 4, 18, 4),
('Perfect olive oil for cooking. Authentic Italian taste.', NOW() - INTERVAL 2 DAY, 5, 19, 3),
('Delicious dark chocolate! Not too bitter, just right.', NOW() - INTERVAL 1 DAY, 4, 20, 6),

-- Comments for Sports
('Perfect for daily jogging. Very comfortable and supportive!', NOW() - INTERVAL 10 DAY, 5, 24, 4),
('Excellent yoga mat. Good grip and perfect thickness.', NOW() - INTERVAL 7 DAY, 5, 25, 3),
('Great quality dumbbells. Adjustment mechanism works smoothly.', NOW() - INTERVAL 4 DAY, 4, 26, 2),

-- Comments for Electronics (Gaming)
('Highly recommended for gaming! Precision and responsiveness are top-notch.', NOW() - INTERVAL 5 DAY, 5, 6, 2),
('Love the keyboard! Cherry MX switches feel amazing.', NOW() - INTERVAL 3 DAY, 5, 7, 5),
('Battery life could be better but overall great phone.', NOW() - INTERVAL 2 DAY, 3, 2, 4);

-- ========================================
-- SAMPLE DATA: orders
-- ========================================
INSERT INTO orders (order_date, total_amount, status, shipping_address, phone, customer_id) VALUES
-- Delivered orders
(NOW() - INTERVAL 20 DAY, 1349.98, 'DELIVERED', '456 Customer Avenue, District 1', '0987654321', 2),
(NOW() - INTERVAL 18 DAY, 949.97, 'DELIVERED', '789 Buyer Boulevard, District 2', '0912345678', 3),
(NOW() - INTERVAL 15 DAY, 89.97, 'DELIVERED', '321 Shop Lane, District 3', '0909123456', 4),
(NOW() - INTERVAL 12 DAY, 179.96, 'DELIVERED', '654 Market Street, District 4', '0898765432', 5),

-- Shipping orders
(NOW() - INTERVAL 5 DAY, 1199.98, 'SHIPPING', '987 Commerce Road, District 5', '0876543210', 6),
(NOW() - INTERVAL 4 DAY, 119.98, 'SHIPPING', '456 Customer Avenue, District 1', '0987654321', 2),

-- Confirmed orders
(NOW() - INTERVAL 2 DAY, 649.97, 'CONFIRMED', '789 Buyer Boulevard, District 2', '0912345678', 3),
(NOW() - INTERVAL 1 DAY, 329.97, 'CONFIRMED', '654 Market Street, District 4', '0898765432', 5),

-- Pending orders
(NOW() - INTERVAL 3 HOUR, 269.97, 'PENDING', '321 Shop Lane, District 3', '0909123456', 4),
(NOW() - INTERVAL 1 HOUR, 149.98, 'PENDING', '987 Commerce Road, District 5', '0876543210', 6);

-- ========================================
-- SAMPLE DATA: order_line
-- ========================================
INSERT INTO order_line (quantity, unit_price, subtotal, product_id, order_id) VALUES
-- Order 1: John Doe (DELIVERED) - Laptop + Book
(1, 1299.99, 1299.99, 1, 1),
(1, 49.99, 49.99, 10, 1),

-- Order 2: Jane Smith (DELIVERED) - Phone + Case
(1, 999.99, 999.99, 3, 2),
(1, 49.99, 49.99, 6, 2),

-- Order 3: Bob Wilson (DELIVERED) - Books
(1, 39.99, 39.99, 9, 3),
(1, 49.99, 49.99, 10, 3),

-- Order 4: Alice Brown (DELIVERED) - Running Shoes + Yoga Mat
(1, 119.99, 119.99, 24, 4),
(2, 29.99, 59.97, 25, 4),

-- Order 5: Charlie Davis (SHIPPING) - iPhone + Accessories
(1, 1199.99, 1199.99, 2, 5),

-- Order 6: John Doe (SHIPPING) - Clothing
(2, 19.99, 39.98, 13, 6),
(1, 79.99, 79.99, 8, 6),

-- Order 7: Jane Smith (CONFIRMED) - Electronics
(1, 599.99, 599.99, 5, 7),
(1, 49.99, 49.99, 32, 7),

-- Order 8: Alice Brown (CONFIRMED) - Food & Sports
(3, 29.99, 89.97, 25, 8),
(2, 119.99, 239.98, 24, 8),

-- Order 9: Bob Wilson (PENDING) - Running Shoes
(3, 89.99, 269.97, 24, 9),

-- Order 10: Charlie Davis (PENDING) - Books
(1, 44.99, 44.99, 11, 10),
(2, 24.99, 49.99, 12, 10),
(1, 44.99, 44.99, 29, 10);

-- ========================================
-- VERIFICATION QUERIES
-- ========================================
-- Uncomment to verify the data

-- SELECT * FROM category;
-- SELECT * FROM customer;
-- SELECT * FROM product;
-- SELECT * FROM comment;
-- SELECT * FROM orders;
-- SELECT * FROM order_line;

-- Count records in each table
-- SELECT 'category' as table_name, COUNT(*) as row_count FROM category
-- UNION ALL SELECT 'customer', COUNT(*) FROM customer
-- UNION ALL SELECT 'product', COUNT(*) FROM product
-- UNION ALL SELECT 'comment', COUNT(*) FROM comment
-- UNION ALL SELECT 'orders', COUNT(*) FROM orders
-- UNION ALL SELECT 'order_line', COUNT(*) FROM order_line;

-- ========================================
-- END OF SCRIPT
-- ========================================
