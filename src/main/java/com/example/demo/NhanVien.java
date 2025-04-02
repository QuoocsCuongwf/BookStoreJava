package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;


@Entity
@Data
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;
    private String ho;
    private String ten;
    private String chucVu;
    private int luong;
    private String cccd;
    private Date ngayVaoLam;;
    private String diaChi;
    private String soDienThoai;

}