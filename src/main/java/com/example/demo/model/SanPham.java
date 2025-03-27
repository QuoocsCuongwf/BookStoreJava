package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SAN_PHAM")
public class SanPham {
    @Id
    @Column(name = "MASP", nullable = false, length = 10)
    private String masp;

    @Column(name = "TENSP", nullable = false, length = 20)
    private String tensp;

    @Column(name = "SL")
    private Integer sl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MATL", nullable = false)
    private com.example.demo.model.TheLoai matl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MATG", nullable = false)
    private com.example.demo.model.TacGia matg;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANXB", nullable = false)
    private NhaXuatBan manxb;

    @Column(name = "NAMXB")
    private Integer namxb;

    @Column(name = "DONGIA")
    private Integer dongia;

    @Column(name = "SOTRANG")
    private Integer sotrang;

}