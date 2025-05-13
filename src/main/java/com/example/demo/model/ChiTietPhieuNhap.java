package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChiTietPhieuNhap {

    @JsonProperty("MAPN")
    private String mapn;


    @JsonProperty("MASP")
    private String masp;

    @JsonProperty("DONGIA")
    private Integer dongia;

    @JsonProperty("SL")
    private Integer sl;

    @JsonProperty("THANHTIEN")
    private Integer thanhtien;

}