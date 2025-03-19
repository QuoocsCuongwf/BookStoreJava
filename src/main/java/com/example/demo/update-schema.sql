CREATE TABLE nhan_vien
(
    id            int IDENTITY (1, 1) NOT NULL,
    ho            varchar(255),
    ten           varchar(255),
    chuc_vu       varchar(255),
    luong         int NOT NULL,
    cccd          varchar(255),
    ngay_vao_lam  date,
    dia_chi       varchar(255),
    so_dien_thoai varchar(255),
    CONSTRAINT pk_nhanvien PRIMARY KEY (id)
)