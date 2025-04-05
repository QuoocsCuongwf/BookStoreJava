package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT")
public class Account {
    @EmbeddedId
    private AccountId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHUCVU", referencedColumnName = "CHUCVU")
    private com.example.demo.model.NhanVien chucvu;

}