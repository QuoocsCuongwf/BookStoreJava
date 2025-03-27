package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ChiTietPhieuNhapId implements java.io.Serializable {
    private static final long serialVersionUID = -5140623821317967834L;
    @Column(name = "MAPN", nullable = false, length = 10)
    private String mapn;

    @Column(name = "MASP", nullable = false, length = 10)
    private String masp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChiTietPhieuNhapId entity = (ChiTietPhieuNhapId) o;
        return Objects.equals(this.mapn, entity.mapn) &&
                Objects.equals(this.masp, entity.masp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapn, masp);
    }

}