package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChiTietHoaDon {

    private String mahd;

    private String masp;

    @Column(name = "SL")
    private Integer sl;

    @Column(name = "DONGIA")
    private Integer dongia;

    @Column(name = "THANHTIEN")
    private Integer thanhtien;

}