package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CHI_TIET_PHIEU_NHAP")
public class ChiTietPhieuNhap {
    @EmbeddedId
    private ChiTietPhieuNhapId id;

    @MapsId("mapn")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MAPN", nullable = false)
    private com.example.demo.model.PhieuNhap mapn;

    @MapsId("masp")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MASP", nullable = false)
    private com.example.demo.model.SanPham masp;

    @Column(name = "DONGIA")
    private Integer dongia;

    @Column(name = "SL")
    private Integer sl;

    @Column(name = "THANHTIEN")
    private Integer thanhtien;

}