package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HoaDon {
    @JsonProperty("MAHD")
    private String mahd;
    @JsonProperty("NGAYLAP")
    private LocalDate ngaylap;
    @JsonProperty("MANV")
    private String manv;
    @JsonProperty("MAKH")
    private String makh;
    @JsonProperty("TONGTIEN")
    private double tongtien;

}