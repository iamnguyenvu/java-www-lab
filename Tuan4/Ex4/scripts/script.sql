create table books
(
    id       int primary key,
    title    varchar(255)   not null,
    author   varchar(255)   not null,
    url      varchar(255),
    price    decimal(10, 2) not null,
    quantity int            not null
)

