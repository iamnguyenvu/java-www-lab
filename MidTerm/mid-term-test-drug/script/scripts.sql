drop database if exists quanlythuoc;
create database quanlythuoc;

alter database iuhbookstore
    character set utf8mb4
    collate utf8mb4_unicode_ci;
use quanlythuoc;

create table LOAITHUOC (
    MALOAI INT PRIMARY KEY,
    TENLOAI NVARCHAR(100) NOT NULL
);

create table THUOC (
    MATHUOC INT PRIMARY KEY AUTO_INCREMENT,
    TENTHUOC NVARCHAR(100) NOT NULL ,
    GIA DOUBLE NOT NULL,
    NAMSX INT NOT NULL,
    MALOAI INT NOT NULL
);

INSERT INTO LOAITHUOC (MALOAI, TENLOAI) VALUES
                                            (1, 'Giảm đau'),
                                            (2, 'Hạ sốt'),
                                            (3, 'Kháng sinh'),
                                            (4, 'Kháng viêm'),
                                            (5, 'Dị ứng/Kháng histamine'),
                                            (6, 'Vitamin & Khoáng chất'),
                                            (7, 'Tiêu hóa'),
                                            (8, 'Hô hấp'),
                                            (9, 'Tim mạch'),
                                            (10, 'Đái tháo đường');

INSERT INTO THUOC (TENTHUOC, GIA, NAMSX, MALOAI) VALUES
                                                     ('Paracetamol 500mg',             12000, 2023, 2),
                                                     ('Efferalgan 500mg',              15000, 2024, 2),
                                                     ('Ibuprofen 400mg',               18000, 2022, 1),
                                                     ('Naproxen 250mg',                22000, 2021, 1),
                                                     ('Amoxicillin 500mg',             35000, 2023, 3),
                                                     ('Azithromycin 250mg',            56000, 2024, 3),
                                                     ('Cefuroxime 250mg',              72000, 2024, 3),
                                                     ('Prednisolone 5mg',              24000, 2022, 4),
                                                     ('Diclofenac 50mg',               26000, 2023, 4),
                                                     ('Loratadine 10mg',               17000, 2024, 5),
                                                     ('Cetirizine 10mg',               16000, 2023, 5),
                                                     ('Vitamin C 1000mg',              20000, 2024, 6),
                                                     ('Vitamin D3 1000 IU',            23000, 2023, 6),
                                                     ('Kẽm Gluconate 70mg',            19000, 2022, 6),
                                                     ('Omeprazole 20mg',               28000, 2024, 7),
                                                     ('Domperidone 10mg',              21000, 2023, 7),
                                                     ('Acetylcysteine 200mg',          25000, 2024, 8),
                                                     ('Dextromethorphan 15mg',         14000, 2023, 8),
                                                     ('Amlodipine 5mg',                30000, 2023, 9),
                                                     ('Losartan 50mg',                 35000, 2022, 9),
                                                     ('Metformin 500mg',               27000, 2024, 10),
                                                     ('Gliclazide MR 30mg',            42000, 2023, 10);
