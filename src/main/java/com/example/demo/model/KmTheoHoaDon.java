package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class KmTheoHoaDon {

    @JsonProperty("MACTKM")
    private String mactkm;

    @JsonProperty("MASP")
    private String masanpham;

    @JsonProperty("PHANTRAMKHUYENMAI")
    private Double phantramkhuyenmai;

    @JsonProperty("SOTIENHOADON")
    private Double tongtien;


    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    // Getter/Setter
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }


}
