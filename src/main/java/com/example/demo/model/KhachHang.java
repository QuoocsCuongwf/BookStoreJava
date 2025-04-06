package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "KHACH_HANG")
public class KhachHang {
    @Id
    @Column(name = "MAKH", nullable = false, length = 10)
    private String makh;

    @Column(name = "HOKH", nullable = false, length = 30)
    private String hokh;

    @Column(name = "TENKH", nullable = false, length = 20)
    private String tenkh;

    @Column(name = "EMAIL", length = 30)
    private String email;

    @Column(name = "SDT", length = 10)
    private String sdt;

    @Column(name = "DIACHI", length = 30)
    private String diachi;


}