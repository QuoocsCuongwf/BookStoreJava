package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class KmTheoSoTienHoaDon extends ChuongTrinhKM {
    @JsonProperty("SOTIENHOADON")
    private int sotienhoadon;

    public KmTheoSoTienHoaDon() {
        super(); // gọi constructor không tham số của lớp cha (đã có)
    }
    public KmTheoSoTienHoaDon(String mactkm, Double phantramkhuyenmai, LocalDate ngayBatDau, LocalDate ngayKetThuc, int sotienhoadon) {
        super(mactkm,phantramkhuyenmai,ngayBatDau,ngayKetThuc);
        this.sotienhoadon=sotienhoadon;

    }




//    @JsonProperty("NGAYBATDAU")
//    private LocalDate ngayBatDau;
//
//    @JsonProperty("NGAYKETTHUC")
//    private LocalDate ngayKetThuc;

    // Getter/Setter
//    public LocalDate getNgayBatDau() { return ngayBatDau; }
//    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }
//
//    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
//    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }


}