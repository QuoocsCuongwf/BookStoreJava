package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("MATG")
    private String matg;

    @Column(name = "HOTG", nullable = false, length = 30)
    @JsonProperty("HOTG")
    private String hotg;

    @Column(name = "TENTG", nullable = false, length = 20)
    @JsonProperty("TENTG")
    private String tentg;

    @Column(name = "QUEQUAN", length = 30)
    @JsonProperty("QUEQUAN")
    private String quequan;

    @Column(name = "NAMSINH")
    @JsonProperty("NAMSINH")
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
