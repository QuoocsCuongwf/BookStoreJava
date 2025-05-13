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
@Table(name = "KHACH_HANG")
public class KhachHang {

    @Id
    @Column(name = "MAKH", nullable = false, length = 10)
    @JsonProperty("MAKH")
    private String makh;

    @Column(name = "HOKH", nullable = false, length = 50)
    @JsonProperty("HOKH")
    private String hokh;

    @Column(name = "TENKH", nullable = false, length = 30)
    @JsonProperty("TENKH")
    private String tenkh;

    @Column(name = "EMAIL", length = 30)
    @JsonProperty("EMAIL")
    private String email;

    @Column(name = "SDT", length = 10)
    @JsonProperty("SDT")
    private String sdt;

    @Column(name = "DIACHI", length = 30)
    @JsonProperty("DIACHI")
    private String diachi;


}