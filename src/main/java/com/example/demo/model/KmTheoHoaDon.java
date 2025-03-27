package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "KM_THEO_HOA_DON")
public class KmTheoHoaDon {
    @EmbeddedId
    private KmTheoHoaDonId id;

    @MapsId("mactkm")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MACTKM", nullable = false)
    private ChuongTrinhKhuyenMai mactkm;

    @Column(name = "PHANTRAMKHUYENMAI")
    private Double phantramkhuyenmai;

}