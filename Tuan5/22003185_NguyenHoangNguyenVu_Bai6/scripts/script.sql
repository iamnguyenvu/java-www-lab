create database news_management;

use news_management;

create table DanhMuc(
                        maDM int primary key auto_increment,
                        tenDanhMuc varchar(20) not null ,
                        nguoiQuanLy varchar(50) not null ,
                        ghiChu varchar(255)
);

create table TinTuc(
                       maTT int primary key auto_increment,
                       tieuDe varchar(100) not null ,
                       noiDungTT varchar(255) not null,
                       lienKet varchar(100) not null,
                       maDM int not null
)