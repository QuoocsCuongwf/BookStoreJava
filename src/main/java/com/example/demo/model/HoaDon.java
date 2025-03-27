package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "HOA_DON")
public class HoaDon {
    @Id
    @Column(name = "MAHD", nullable = false, length = 10)
    private String mahd;

    @Column(name = "NGAYLAP")
    private LocalDate ngaylap;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANV", nullable = false)
    private com.example.demo.model.NhanVien manv;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MAKH", nullable = false)
    private com.example.demo.model.KhachHang makh;

    @Column(name = "TONGTIEN")
    private Integer tongtien;

}