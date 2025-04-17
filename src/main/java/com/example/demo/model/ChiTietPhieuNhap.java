package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChiTietPhieuNhap {

    private String mapn;

    private String masp;

    private Integer dongia;

    private Integer sl;

    private Integer thanhtien;

}