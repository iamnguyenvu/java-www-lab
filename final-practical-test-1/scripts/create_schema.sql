-- MariaDB compatible schema & seed for Department–Employee
-- Tested with MariaDB 10.6+

-- Optional: create and use database
CREATE DATABASE IF NOT EXISTS dept_emp
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE dept_emp;

-- Safety: drop tables if exist (respect FK order)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;
SET FOREIGN_KEY_CHECKS = 1;

-- SCHEMA ----------------------------------------------------------------------

CREATE TABLE departments (
                             id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                             code VARCHAR(10) NOT NULL,
                             name VARCHAR(100) NOT NULL,
                             description VARCHAR(255) NULL,
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (id),
                             CONSTRAINT uk_departments_code UNIQUE (code),
                             CONSTRAINT uk_departments_name UNIQUE (name)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE employees (
                           id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                           full_name VARCHAR(100) NOT NULL,
                           email VARCHAR(150) NOT NULL,
                           salary DECIMAL(12,2) NOT NULL,
                           hire_date DATE NOT NULL,
                           department_id BIGINT UNSIGNED NOT NULL,
                           active TINYINT(1) NOT NULL DEFAULT 1,
                           PRIMARY KEY (id),
                           CONSTRAINT uk_employees_email UNIQUE (email),
                           CONSTRAINT chk_employees_salary_positive CHECK (salary > 0),
                           CONSTRAINT fk_employees_department
                               FOREIGN KEY (department_id) REFERENCES departments(id)
                                   ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

-- SEED ------------------------------------------------------------------------

INSERT INTO departments(code, name, description) VALUES
                                                     ('HR',  'Human Resources', 'People operations'),
                                                     ('ENG', 'Engineering',     'Product & platform'),
                                                     ('FIN', 'Finance',         'Accounting & FP&A');

INSERT INTO employees(full_name,email,salary,hire_date,department_id,active)
VALUES
    ('Nguyen Van A','a.hr@example.com', 1200.00,'2022-04-02', (SELECT id FROM departments WHERE code='HR'), 1),
    ('Tran Thi B',  'b.hr@example.com', 1500.50,'2023-01-10', (SELECT id FROM departments WHERE code='HR'), 1),
    ('Le Van C',    'c.eng@example.com',2500.00,'2022-11-05', (SELECT id FROM departments WHERE code='ENG'),1),
    ('Pham Thi D',  'd.eng@example.com',2800.00,'2023-05-19', (SELECT id FROM departments WHERE code='ENG'),1),
    ('Hoang Van E', 'e.eng@example.com',2200.00,'2021-09-30', (SELECT id FROM departments WHERE code='ENG'),1),
    ('Do Thi F',    'f.fin@example.com',1800.75,'2024-02-12', (SELECT id FROM departments WHERE code='FIN'),1),
    ('Vo Van G',    'g.fin@example.com',1750.00,'2023-07-21', (SELECT id FROM departments WHERE code='FIN'),1),
    ('Bui Thi H',   'h.fin@example.com',1650.00,'2022-12-01', (SELECT id FROM departments WHERE code='FIN'),1);

-- Index (optional for search/filter)
CREATE INDEX idx_employees_full_name ON employees(full_name);
CREATE INDEX idx_employees_department_id ON employees(department_id);
