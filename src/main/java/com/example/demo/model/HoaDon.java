package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HoaDon {
    private String mahd;

    private LocalDate ngaylap;

    private String manv;

    private String makh;

    private Integer tongtien;

}