package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class KhuyenMai {


    private String mactkm;

    private String masanpham;

    private Double phantramkhuyenmai;

    private Double tongtien;


    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    // Getter/Setter
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }


}
