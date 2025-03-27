package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "THE_LOAI")
public class TheLoai {
    @Id
    @Column(name = "MATL", nullable = false, length = 10)
    private String matl;

    @Column(name = "TENTL", nullable = false, length = 20)
    private String tentl;

}