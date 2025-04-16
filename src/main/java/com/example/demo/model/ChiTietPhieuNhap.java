package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChiTietPhieuNhap {
    @EmbeddedId
    private ChiTietPhieuNhapId id;

    private String mapn;

    private String masp;

    @Column(name = "DONGIA")
    private Integer dongia;

    @Column(name = "SL")
    private Integer sl;

    @Column(name = "THANHTIEN")
    private Integer thanhtien;

}