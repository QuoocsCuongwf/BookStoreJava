package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PhieuNhap {
    private String mapn;

    private LocalDate ngaynhap;

    private String manv;

    private String mancc;

    private Double tongtien;

}