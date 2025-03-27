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
public class KmTheoHoaDonId implements java.io.Serializable {
    private static final long serialVersionUID = -9052969603279718790L;
    @Column(name = "MACTKM", nullable = false, length = 10)
    private String mactkm;

    @Column(name = "SOTIENHOADON", nullable = false)
    private Integer sotienhoadon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KmTheoHoaDonId entity = (KmTheoHoaDonId) o;
        return Objects.equals(this.mactkm, entity.mactkm) &&
                Objects.equals(this.sotienhoadon, entity.sotienhoadon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mactkm, sotienhoadon);
    }

}