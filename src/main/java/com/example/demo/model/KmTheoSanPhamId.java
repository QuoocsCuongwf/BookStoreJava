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
public class KmTheoSanPhamId implements java.io.Serializable {
    private static final long serialVersionUID = 753824953017608738L;
    @Column(name = "MACTKM", nullable = false, length = 10)
    private String mactkm;

    @Column(name = "MASP", nullable = false, length = 10)
    private String masp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KmTheoSanPhamId entity = (KmTheoSanPhamId) o;
        return Objects.equals(this.mactkm, entity.mactkm) &&
                Objects.equals(this.masp, entity.masp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mactkm, masp);
    }

}