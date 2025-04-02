package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "NHAN_VIEN")
public class NhanVien {
    @Id
    @Column(name = "MANV", nullable = false, length = 10)
    private String manv;

    @Column(name = "HONV", nullable = false, length = 50)
    private String honv;

    @Column(name = "TENNV", nullable = false, length = 30)
    private String tennv;

    @Column(name = "CHUCVU", length = 30)
    private String chucvu;

    @Column(name = "CCCD", length = 15)
    private String cccd;

    @Column(name = "LUONG")
    private Integer luong;

    @Column(name = "NGAYVAOLAM")
    private LocalDate ngayvaolam;

    @Column(name = "MAIL", length = 30)
    private String mail;

}