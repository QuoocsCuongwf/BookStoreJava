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
@Table(name = "TAC_GIA")
public class TacGia {
    @Id
    @Column(name = "MATG", nullable = false, length = 10)
    private String matg;

    @Column(name = "HOTG", nullable = false, length = 30)
    private String hotg;

    @Column(name = "TENTG", nullable = false, length = 20)
    private String tentg;

    @Column(name = "QUEQUAN", length = 30)
    private String quequan;

    @Column(name = "NAMSINH")
    private Integer namsinh;
    private void addTacGia() {
        // Xử lý thêm tác giả
    }


    private void clossInforContainer() {
        // Xử lý đóng form
    }


    private void timKiem() {
        // Xử lý tìm kiếm
    }


    private void openInforContainer() {
        // Mở khung nhập thông tin
    }
}

}