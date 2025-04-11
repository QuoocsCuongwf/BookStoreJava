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
@Table(name = "NHA_XUAT_BAN")
public class NhaXuatBan {
    @Id
    @Column(name = "MANXB", nullable = false, length = 10)
    @JsonProperty("MANXB")
    private String manxb;

    @Column(name = "TENNXB", nullable = false, length = 20)
    @JsonProperty("TENNXB")
    private String tennxb;

    @Column(name = "DIACHI", length = 50)
    @JsonProperty("DIACHI")
    private String diachi;

    @Column(name = "SDT", length = 10)
    @JsonProperty("SDT")
    private String sdt;

    public NhaXuatBan() {}

    public NhaXuatBan(String manxb, String tennxb, String diachi, String sdt) {
        this.manxb = manxb;
        this.tennxb = tennxb;
        this.diachi = diachi;
        this.sdt = sdt;
    }
}