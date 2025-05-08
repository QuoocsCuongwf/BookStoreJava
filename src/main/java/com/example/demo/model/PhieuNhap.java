package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "PHIEU_NHAP")
public class PhieuNhap {
    @Id
    @JsonProperty("MAPN")
    private String mapn;

    @JsonProperty("NGAYNHAP")
    private LocalDate ngaynhap;

    @JsonProperty("MANV")
    private String manv;

    @JsonProperty("MANCC")
    private String mancc;

    @Column(name = "TONGTIEN", nullable = false)
    @JsonProperty("TONGTIEN")
    private int tongtien;


}