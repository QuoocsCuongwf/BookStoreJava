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
@Table(name = "NHA_CUNG_CAP")
public class NhaCungCap {
    @Id
    @Column(name = "MANCC", nullable = false, length = 10)
    private String mancc;

    @Column(name = "TENNCC", nullable = false, length = 20)
    private String tenncc;

    @Column(name = "THONGTIN", length = 50)
    private String thongtin;

}