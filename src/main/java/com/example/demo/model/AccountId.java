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
public class AccountId implements java.io.Serializable {
    private static final long serialVersionUID = 1638040613098408877L;
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 30)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccountId entity = (AccountId) o;
        return Objects.equals(this.password, entity.password) &&
                Objects.equals(this.username, entity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, username);
    }

}