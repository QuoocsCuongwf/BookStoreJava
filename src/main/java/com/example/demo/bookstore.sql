﻿CREATE DATABASE bookstore;
DROP DATABASE bookstore
use bookstore;

--
CREATE TABLE KHACH_HANG
(
    MAKH VARCHAR(10) NOT NULL,--PRIMARIKEY
    HOKH VARCHAR(50) NOT NULL,
    TENKH VARCHAR(30) NOT NULL,
    EMAIL VARCHAR(30),
    SDT VARCHAR(10),
    DIACHI VARCHAR(30),
    PRIMARY KEY (MAKH)
)
--
CREATE TABLE TAC_GIA
(
    MATG VARCHAR(10) NOT NULL,--PRIMARIKEY
    HOTG VARCHAR(30) NOT NULL,
    TENTG VARCHAR(20) NOT NULL,
    QUEQUAN VARCHAR(30) ,
    NAMSINH INT ,
    PRIMARY KEY (MATG)
)
--
CREATE TABLE NHAN_VIEN
(
    MANV VARCHAR(10) NOT NULL,--PRIMARIKEY
    HONV VARCHAR(50) NOT NULL,
    TENNV VARCHAR(30) NOT NULL,
    CHUCVU VARCHAR(30) ,
    CCCD VARCHAR(15),
    LUONG INT,
    NGAYVAOLAM DATE,
    MAIL VARCHAR(30),
    PRIMARY KEY (MANV)
)
--
CREATE TABLE NHA_XUAT_BAN
(
    MANXB VARCHAR(10) NOT NULL,--PRIMARIKEY
    TENNXB VARCHAR(20) NOT NULL,
    DIACHI VARCHAR(50),
    SDT VARCHAR(10),
    PRIMARY KEY (MANXB)
)
--
CREATE TABLE NHA_CUNG_CAP
(
    MANCC VARCHAR(10) NOT NULL,--PRIMARIKEY
    TENNCC VARCHAR(20) NOT NULL,
    SDT VARCHAR(20),
    DIACHI VARCHAR(255),
    EMAIL VARCHAR(100),
    PRIMARY KEY (MANCC)
)
CREATE TABLE THE_LOAI
(
    MATL VARCHAR(10) NOT NULL, --PRIMARIKEY
    TENTL VARCHAR(20) NOT NULL,
    PRIMARY KEY (MATL)
)
--------- CHẠY OKE
CREATE TABLE SAN_PHAM
(
    MASP VARCHAR(10) NOT NULL, -- PRIMARY KEY
    TENSP VARCHAR(20) NOT NULL,
    SL INT,
    MATL VARCHAR(10) NOT NULL, -- Khóa ngoại tham chiếu đến THE_LOAI
    MATG VARCHAR(10) NOT NULL, -- Khóa ngoại tham chiếu đến TAC_GIA
    MANXB VARCHAR(10) NOT NULL, -- Khóa ngoại tham chiếu Ađến NHA_XUAT_BAN
    NAMXB INT,
    DONGIA INT,
    SOTRANG INT,
    ANHBIA CHAR(100),
    PRIMARY KEY (MASP), -- Định nghĩa khóa chính
    CONSTRAINT FK_MATL FOREIGN KEY (MATL) REFERENCES THE_LOAI(MATL), -- Khóa ngoại 1
    CONSTRAINT FK_MATG FOREIGN KEY (MATG) REFERENCES TAC_GIA(MATG), -- Khóa ngoại 2
    CONSTRAINT FK_MANXB FOREIGN KEY (MANXB) REFERENCES NHA_XUAT_BAN(MANXB) -- Khóa ngoại 3
);


--CHAY OKE
CREATE TABLE HOA_DON
(
    MAHD VARCHAR(10) NOT NULL,
    NGAYLAP DATE,
    MANV VARCHAR(10) NOT NULL,
    MAKH VARCHAR(10) NOT NULL,
    TONGTIEN INT,
    PRIMARY KEY (MAHD),
    CONSTRAINT FK_MANV FOREIGN KEY (MANV) REFERENCES NHAN_VIEN(MANV),
    CONSTRAINT FK_MAKH FOREIGN KEY (MAKH) REFERENCES KHACH_HANG(MAKH)
)
--CHAY OKE
CREATE TABLE CHI_TIET_HOA_DON
(
    MAHD VARCHAR(10) NOT NULL,
    MASP VARCHAR(10) NOT NULL,
    SL INT,
    DONGIA INT,
    THANHTIEN INT,
    PRIMARY KEY (MAHD,MASP),
    CONSTRAINT FK_MAHD FOREIGN KEY (MAHD) REFERENCES HOA_DON(MAHD),
    CONSTRAINT FK_MASPHD FOREIGN KEY (MASP) REFERENCES SAN_PHAM(MASP)
)
--CHAY OKE
CREATE TABLE CHUONG_TRINH_KHUYEN_MAI
(
    MACTKM VARCHAR(10) NOT NULL,
    NGAYBD DATE,
    NGAYKT DATE,
    PRIMARY KEY (MACTKM)
)
CREATE TABLE KM_THEO_SAN_PHAM
(
    MACTKM  VARCHAR(10) NOT NULL,
    MASP VARCHAR(10) NOT  NULL,
    PHANTRAMKHUYENMAI FLOAT ,
    PRIMARY KEY (MACTKM,MASP),
    CONSTRAINT FK_MAHDKM FOREIGN KEY (MACTKM) REFERENCES CHUONG_TRINH_KHUYEN_MAI(MACTKM),
    CONSTRAINT FK_MASPKM FOREIGN KEY (MASP) REFERENCES SAN_PHAM(MASP)
)
CREATE TABLE KM_THEO_HOA_DON
(
    MACTKM VARCHAR(10) NOT NULL,
    SOTIENHOADON INT NOT  NULL,
    PHANTRAMKHUYENMAI FLOAT ,
    PRIMARY KEY (MACTKM,SOTIENHOADON),
    CONSTRAINT FK_MAHDK FOREIGN KEY (MACTKM) REFERENCES CHUONG_TRINH_KHUYEN_MAI(MACTKM)
)
-- CHAY OKE
CREATE TABLE PHIEU_NHAP
(
    MAPN VARCHAR(10) NOT NULL,
    NGAYNHAP DATE,
    MANV VARCHAR(10) NOT NULL,
    MANCC VARCHAR(10) NOT NULL,
    TONGTIEN INT,
    PRIMARY KEY (MAPN),
    CONSTRAINT FK_MANVPN FOREIGN KEY (MANV) REFERENCES NHAN_VIEN(MANV),
    CONSTRAINT FK_MANCC FOREIGN KEY (MANCC) REFERENCES NHA_CUNG_CAP(MANCC)

)
CREATE TABLE CHI_TIET_PHIEU_NHAP
(
    MAPN VARCHAR(10) NOT NULL,
    MASP VARCHAR(10) NOT NULL,
    DONGIA INT,
    SL INT,
    THANHTIEN INT,
    PRIMARY KEY (MAPN,MASP),
    CONSTRAINT FK_MAPN FOREIGN KEY (MAPN) REFERENCES PHIEU_NHAP(MAPN),
    CONSTRAINT FK_MASP FOREIGN KEY (MASP) REFERENCES SAN_PHAM(MASP)
)
ALTER TABLE NHAN_VIEN ADD CONSTRAINT UC_CHUCVU UNIQUE (CHUCVU);

CREATE TABLE ACCOUNT
(
    USERNAME CHAR(50) NOT NULL,
    PASSWORD CHAR(30),
    MAVN VARCHAR(10),
    PRIMARY KEY(USERNAME),
)

ALTER TABLE ACCOUNT
    ADD CONSTRAINT FK_ROLE FOREIGN KEY (MAVN) REFERENCES NHAN_VIEN(MANV);






INSERT INTO KHACH_HANG VALUES
('KH01', 'Nguyen', 'An', 'an.nguyen@gmail.com', '0901234567', '123 Le Loi'),
('KH02', 'Tran', 'Binh', 'binh.tran@yahoo.com', '0902345678', '45 Nguyen Trai');

INSERT INTO TAC_GIA VALUES
('TG01', 'Nguyen', 'Nhat', 'Ha Noi', 1980),
('TG02', 'Le', 'Quyen', 'Hue', 1975);

INSERT INTO NHAN_VIEN VALUES
('NV01', 'Pham', 'Hoa', 'Quan ly', '123456789012', 10000000, '2020-01-15', 'hoa.pham@book.vn'),
('NV02', 'Do', 'Linh', 'Thu ngan', '987654321098', 7000000, '2021-05-10', 'linh.do@book.vn');

INSERT INTO NHA_XUAT_BAN VALUES
('NXB01', 'NXB Tre', '1 Nguyen Van Cu', '0281234567'),
('NXB02', 'NXB Kim Dong', '5 Dinh Tien Hoang', '0287654321');

INSERT INTO NHA_CUNG_CAP VALUES
('NCC01', 'Cong ty ABC', '0908765432', '12 Tran Hung Dao', 'abc@nhacc.vn'),
('NCC02', 'Cong ty XYZ', '0912345678', '34 Ly Thuong Kiet', 'xyz@nhacc.vn');

INSERT INTO THE_LOAI VALUES
('TL01', 'Tieu thuyet'),
('TL02', 'Giao trinh');

INSERT INTO SAN_PHAM VALUES
('SP01', 'Dac Nhan Tam', 50, 'TL01', 'TG01', 'NXB01', 2020, 100000, 250, 'book1.jpg'),
 ('SP02', 'Lap trinh C', 100, 'TL02', 'TG02', 'NXB02', 2022, 120000, 300, 'book2.jpg');

INSERT INTO HOA_DON VALUES
('HD01', '2024-04-01', 'NV01', 'KH01', 200000),
('HD02', '2024-04-03', 'NV02', 'KH02', 120000);

INSERT INTO CHI_TIET_HOA_DON VALUES
('HD01', 'SP01', 2, 100000, 200000),
('HD02', 'SP02', 1, 120000, 120000);

INSERT INTO CHUONG_TRINH_KHUYEN_MAI VALUES
('KM01', '2024-04-01', '2024-04-10'),
('KM02', '2024-04-11', '2024-04-20');

INSERT INTO KM_THEO_SAN_PHAM VALUES
('KM01', 'SP01', 0.1),
('KM02', 'SP02', 0.2);

INSERT INTO KM_THEO_HOA_DON VALUES
('KM01', 200000, 0.15),
('KM02', 300000, 0.2);

INSERT INTO PHIEU_NHAP VALUES
('PN01', '2024-03-25', 'NV01', 'NCC01', 500000),
('PN02', '2024-03-26', 'NV02', 'NCC02', 300000);

INSERT INTO CHI_TIET_PHIEU_NHAP VALUES
('PN01', 'SP01', 90000, 10, 900000),
('PN02', 'SP02', 100000, 3, 300000);

INSERT INTO ACCOUNT VALUES
('admin1', 'pass123', 'NV01'),
('thu_ngan', '123456', 'NV02');
