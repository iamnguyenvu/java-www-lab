CREATE table products
(
    id          int primary key,
    model       varchar(255)   not null,
    price       decimal(10, 2) not null,
    quantity    int            not null,
    description text,
    imgUrl      varchar(255)
);

INSERT INTO products (id, model, price, quantity, description, imgUrl)
VALUES (1, 'Laptop Dell XPS 13', 1299.99, 10, 'Ultrabook mỏng nhẹ, màn hình 13 inch, CPU Intel Core i7', 'laptop-dell-xps13.jpg'),
       (2, 'iPhone 15 Pro', 999.00, 15, 'Điện thoại Apple mới nhất với chip A17 Bionic', 'iphone-15-pro.jpg'),
       (3, 'Samsung Galaxy S24', 899.50, 20, 'Smartphone Samsung màn hình Dynamic AMOLED 2X', 'samsung-galaxy-s24.jpg'),
       (4, 'Tai nghe Sony WH-1000XM5', 349.99, 25, 'Tai nghe chống ồn cao cấp của Sony', 'sony-wh1000xm5.jpg'),
       (5, 'Chuột Logitech MX Master 3', 99.99, 30, 'Chuột không dây đa chức năng dành cho dân văn phòng', 'logitech-mx-master3.jpg');
