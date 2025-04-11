package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PHIEU_NHAP")
public class PhieuNhap {
    @Id
    @Column(name = "MAPN", nullable = false, length = 10)
    private String mapn;

    @Column(name = "NGAYNHAP")
    private LocalDate ngaynhap;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANV", nullable = false)
    private NhanVien manv;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANCC", nullable = false)
    private NhaCungCap mancc;

    @Column(name = "TONGTIEN")
    private Integer tongtien;

}