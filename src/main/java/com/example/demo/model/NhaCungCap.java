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
@Table(name = "NHA_CUNG_CAP")
public class NhaCungCap {
    @Id
    @Column(name = "MANCC", nullable = false, length = 10)
    @JsonProperty("MANCC")
    private String maNhaCungCap;

    @Column(name = "TENNCC", nullable = false, length = 100)
    @JsonProperty("TENNCC")
    private String tenNhaCungCap;

    @Column(name = "DIACHI", length = 255)
    @JsonProperty("DIACHI")
    private String diaChi;

    @Column(name = "SDT", length = 20)
    @JsonProperty("SDT")
    private String sdt;

    @Column(name = "EMAIL", length = 50)
    @JsonProperty("EMAIL")
    private String email;

}
