package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class KmTheoSanPham extends ChuongTrinhKM {
    @JsonProperty("MASP")
    private String masp;

    public KmTheoSanPham() {
        super(); // gọi constructor mặc định của lớp cha
    }

    public KmTheoSanPham(String mactkm,Double phantramkhuyenmai, LocalDate ngayBatDau,LocalDate ngayKetThuc,String masp) {
        super(mactkm,phantramkhuyenmai,ngayBatDau,ngayKetThuc);
        this.masp = masp;
    }

}