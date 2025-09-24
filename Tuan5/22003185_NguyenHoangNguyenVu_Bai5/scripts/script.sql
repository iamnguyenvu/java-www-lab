create database employee_management;
use employee_management;

create table departments (
    id int primary key auto_increment,
    name varchar(50) not null
);

create table employees (
    id int primary key auto_increment,
    name varchar(50) not null,
    department_id int references departments(id),
    salary decimal(10, 2) not null
)