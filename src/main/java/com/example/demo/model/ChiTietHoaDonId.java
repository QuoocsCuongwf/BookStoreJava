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
public class ChiTietHoaDonId implements java.io.Serializable {
    private static final long serialVersionUID = 4682876278593713490L;
    @Column(name = "MAHD", nullable = false, length = 10)
    private String mahd;

    @Column(name = "MASP", nullable = false, length = 10)
    private String masp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChiTietHoaDonId entity = (ChiTietHoaDonId) o;
        return Objects.equals(this.masp, entity.masp) &&
                Objects.equals(this.mahd, entity.mahd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(masp, mahd);
    }

}