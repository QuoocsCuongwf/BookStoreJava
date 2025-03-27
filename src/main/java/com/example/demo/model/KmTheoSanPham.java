package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "KM_THEO_SAN_PHAM")
public class KmTheoSanPham {
    @EmbeddedId
    private KmTheoSanPhamId id;

    @MapsId("mactkm")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MACTKM", nullable = false)
    private ChuongTrinhKhuyenMai mactkm;

    @MapsId("masp")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MASP", nullable = false)
    private com.example.demo.model.SanPham masp;

    @Column(name = "PHANTRAMKHUYENMAI")
    private Double phantramkhuyenmai;

}