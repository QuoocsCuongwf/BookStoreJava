package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChiTietHoaDon {
    @JsonProperty("MAHD")
    private String mahd;
    @JsonProperty("MASP")
    private String masp;
    @JsonProperty("SL")
    @Column(name = "SL")
    private Integer sl;
    @JsonProperty("DONGIA")
    private Integer dongia;

    @JsonProperty("THANHTIEN")
    private Integer thanhtien;
    @Override
    public String toString() {
        return masp + ",   " + sl+ "  "+dongia+"  "+thanhtien;
    }

}