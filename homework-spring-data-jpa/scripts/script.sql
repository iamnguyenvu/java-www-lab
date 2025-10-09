drop database if exists homework_spring_data_jpa;

create database if not exists homework_spring_data_jpa;
use homework_spring_data_jpa;

DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

CREATE TABLE departments
(
    dept_id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    dept_name VARCHAR(100)    NOT NULL UNIQUE,
    PRIMARY KEY (dept_id)
) ENGINE = InnoDB;

CREATE TABLE employees
(
    emp_id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    emp_name VARCHAR(120)    NOT NULL,
    dob      DATE            NULL,
    salary   DECIMAL(15, 2)  NULL,
    dept_id  BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (emp_id),
    FOREIGN KEY (dept_id) REFERENCES departments (dept_id) ON DELETE CASCADE
) ENGINE = InnoDB;

INSERT INTO departments (dept_name) VALUES ('IT Department');
INSERT INTO departments (dept_name) VALUES ('HR Department');
INSERT INTO departments (dept_name) VALUES ('Finance Department');

INSERT INTO employees (emp_name, dob, salary, dept_id) VALUES ('Nguyen Van A', '1990-05-15', 15000000, 1);
INSERT INTO employees (emp_name, dob, salary, dept_id) VALUES ('Tran Thi B', '1995-08-20', 12000000, 1);
INSERT INTO employees (emp_name, dob, salary, dept_id) VALUES ('Le Van C', '1988-03-10', 20000000, 2);
INSERT INTO employees (emp_name, dob, salary, dept_id) VALUES ('Pham Thi D', '1992-11-25', 18000000, 2);
INSERT INTO employees (emp_name, dob, salary, dept_id) VALUES ('Hoang Van E', '1987-07-08', 25000000, 3);


