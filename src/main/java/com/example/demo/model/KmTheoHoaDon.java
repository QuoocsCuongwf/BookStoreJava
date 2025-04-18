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

    @Column(name = "MACTKM", insertable = false, updatable = false)
    private String mactkm;

    @Column(name = "PHANTRAMKHUYENMAI")
    private Double phantramkhuyenmai;

    @Column(name = "TONGTIEN")
    private Double tongtien;
}
