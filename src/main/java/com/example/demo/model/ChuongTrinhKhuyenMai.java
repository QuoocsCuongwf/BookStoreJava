package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChuongTrinhKhuyenMai {

    @JsonProperty("MACTKM")
    private String mactkm;
    @JsonProperty("PHANTRAMKHUYENMAI")
    private Double phantramkhuyenmai;
    @JsonProperty("NGAYBATDAU")
    private LocalDate ngayBatDau;
    @JsonProperty("NGAYKETTHUC")
    private LocalDate ngayKetThuc;


    public ChuongTrinhKhuyenMai() {}

    public ChuongTrinhKhuyenMai(String mactkm,Double phantramkhuyenmai, LocalDate ngayBatDau,LocalDate ngayKetThuc) {
        this.mactkm = mactkm;
        this.phantramkhuyenmai=phantramkhuyenmai
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
}