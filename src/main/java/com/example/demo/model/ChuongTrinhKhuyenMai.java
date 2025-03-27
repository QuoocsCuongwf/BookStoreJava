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
@Table(name = "CHUONG_TRINH_KHUYEN_MAI")
public class ChuongTrinhKhuyenMai {
    @Id
    @Column(name = "MACTKM", nullable = false, length = 10)
    private String mactkm;

    @Column(name = "NGAYBD")
    private LocalDate ngaybd;

    @Column(name = "NGAYKT")
    private LocalDate ngaykt;

}