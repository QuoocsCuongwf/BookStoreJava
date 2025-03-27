package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CHI_TIET_HOA_DON")
public class ChiTietHoaDon {
    @EmbeddedId
    private ChiTietHoaDonId id;

    @MapsId("mahd")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MAHD", nullable = false)
    private com.example.demo.model.HoaDon mahd;

    @MapsId("masp")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MASP", nullable = false)
    private com.example.demo.model.SanPham masp;

    @Column(name = "SL")
    private Integer sl;

    @Column(name = "DONGIA")
    private Integer dongia;

    @Column(name = "THANHTIEN")
    private Integer thanhtien;

}