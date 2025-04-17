package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SanPham {
    @JsonProperty("MASP")
    private String masp;

    @JsonProperty("TENSP")
    private String tensp;

    @JsonProperty("SL")
    private Integer sl;

    @JsonProperty("MATL")
    private String matl;

    @JsonProperty("MATG")
    private String matg;

    @JsonProperty("MANXB")
    private String manxb;

    @JsonProperty("NAMXB")
    private Integer namxb;

    @JsonProperty("DONGIA")
    private Integer dongia;

    @JsonProperty("SOTRANG")
    private Integer sotrang;

    @JsonProperty("ANHBIA")
    private String anhbia;

    public SanPham(){}


}