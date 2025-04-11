package com.example.demo.model;

import com.example.demo.databaseAccesssObject.deserialize.SanPhamDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SAN_PHAM")
@JsonDeserialize(using = SanPhamDeserializer.class)
public class SanPham {
    @Id
    @Column(name = "MASP", nullable = false, length = 10)
    @JsonProperty("MASP")
    private String masp;

    @Column(name = "TENSP", nullable = false, length = 20)
    @JsonProperty("TENSP")
    private String tensp;

    @Column(name = "SL")
    @JsonProperty("SL")
    private Integer sl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MATL", nullable = false)
    @JsonProperty("MATL")
    private TheLoai matl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MATG", nullable = false)
    @JsonProperty("MATG")
    private TacGia matg;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANXB", nullable = false)
    @JsonProperty("MANXB")
    private NhaXuatBan manxb;

    @Column(name = "NAMXB")
    @JsonProperty("NAMXB")
    private Integer namxb;

    @Column(name = "DONGIA")
    @JsonProperty("DONGIA")
    private Integer dongia;

    @Column(name = "SOTRANG")
    @JsonProperty("SOTRANG")
    private Integer sotrang;

    @Column(name = "ANHBIA", length = 100)
    @JsonProperty("ANHBIA")
    private String anhbia;

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    public TheLoai getMatl() {
        return matl;
    }

    public void setMatl(TheLoai matl) {
        this.matl = matl;
    }

    public TacGia getMatg() {
        return matg;
    }

    public void setMatg(TacGia matg) {
        this.matg = matg;
    }

    public NhaXuatBan getManxb() {
        return manxb;
    }

    public void setManxb(NhaXuatBan manxb) {
        this.manxb = manxb;
    }

    public Integer getNamxb() {
        return namxb;
    }

    public void setNamxb(Integer namxb) {
        this.namxb = namxb;
    }

    public Integer getDongia() {
        return dongia;
    }

    public void setDongia(Integer dongia) {
        this.dongia = dongia;
    }

    public Integer getSotrang() {
        return sotrang;
    }

    public void setSotrang(Integer sotrang) {
        this.sotrang = sotrang;
    }

    public String getAnhbia() {
        return anhbia;
    }

    public void setAnhbia(String anhbia) {
        this.anhbia = anhbia;
    }

    public SanPham(){}


}