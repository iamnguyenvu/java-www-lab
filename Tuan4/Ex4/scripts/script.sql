create database if not exists iuhbookstore;
use iuhbookstore;

create table books
(
    id       int primary key,
    title    varchar(255)   not null,
    author   varchar(255)   not null,
    url      varchar(255),
    price    decimal(10, 2) not null,
    quantity int            not null
);

alter database iuhbookstore
    character set utf8mb4
    collate utf8mb4_unicode_ci;

alter table books
    convert to character set utf8mb4
        collate utf8mb4_unicode_ci;

insert into books (id, title, author, url, price, quantity)
values (1, 'Hai số phận', 'Jeffrey Archer', 'hai-so-phan.png', 120000.00, 15),
       (2, 'Kiêu hãnh và định kiến', 'Jane Austen', 'kieu-hanh-va-dinh-kien.png', 95000.00, 20),
       (3, 'Bắt trẻ đồng xanh', 'J.D. Salinger', 'bat-tre-dong-xanh.png', 85000.00, 18),
       (4, 'Cuốn theo chiều gió', 'Margaret Mitchell', 'cuon-theo-chieu-gio.png', 150000.00, 12),
       (5, 'Đời gió hú', 'Emily Brontë', 'doi-gio-hu.png', 98000.00, 14),
       (6, 'Giết con chim nhại', 'Harper Lee', 'giet-con-chim-nhai.png', 89000.00, 16),
       (7, 'Không gia đình', 'Hector Malot', 'khong-gia-dinh.png', 92000.00, 20),
       (8, 'Phía sau nghi can X', 'Keigo Higashino', 'phia-sau-nghi-can-x.png', 105000.00, 10),
       (9, 'Trăm năm cô đơn', 'Gabriel García Márquez', 'tram-nam-co-don.png', 132000.00, 15);
