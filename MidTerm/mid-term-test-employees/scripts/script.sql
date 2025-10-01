create database quanlynhanvien;
use quanlynhanvien;

alter database char set utf8mb4 collate utf8mb4_unicode_ci;

START TRANSACTION;

INSERT IGNORE INTO departments (name) VALUES
                                          ('Engineering'),
                                          ('Human Resources'),
                                          ('Finance'),
                                          ('Marketing'),
                                          ('Sales'),
                                          ('IT Support');

INSERT IGNORE INTO employees (name, department_id, salary) VALUES
                                                               ('Alice Nguyen',   (SELECT id FROM departments WHERE name = 'Engineering'      LIMIT 1), 15000000.00),
                                                               ('Bob Tran',       (SELECT id FROM departments WHERE name = 'Engineering'      LIMIT 1), 22000000.00),
                                                               ('Charlie Pham',   (SELECT id FROM departments WHERE name = 'Engineering'      LIMIT 1), 18000000.00),

                                                               ('Daisy Le',       (SELECT id FROM departments WHERE name = 'Human Resources'  LIMIT 1), 14000000.00),
                                                               ('Ethan Vo',       (SELECT id FROM departments WHERE name = 'Human Resources'  LIMIT 1), 16000000.00),

                                                               ('Fiona Ho',       (SELECT id FROM departments WHERE name = 'Finance'          LIMIT 1), 25000000.00),
                                                               ('George Do',      (SELECT id FROM departments WHERE name = 'Finance'          LIMIT 1), 21000000.00),

                                                               ('Hannah Ly',      (SELECT id FROM departments WHERE name = 'Marketing'        LIMIT 1), 17000000.00),
                                                               ('Ian Phan',       (SELECT id FROM departments WHERE name = 'Marketing'        LIMIT 1), 15500000.00),

                                                               ('Jenny Vu',       (SELECT id FROM departments WHERE name = 'Sales'            LIMIT 1), 20000000.00),
                                                               ('Kevin Bui',      (SELECT id FROM departments WHERE name = 'Sales'            LIMIT 1), 19500000.00),

                                                               ('Linda Dang',     (SELECT id FROM departments WHERE name = 'IT Support'       LIMIT 1), 16000000.00);

COMMIT;