package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhaXuatBan {
    @JsonProperty("MANXB")
    private String maNhaXuatBan;

    @JsonProperty("TENNXB")
    private String tenNhaXuatBan;

    @JsonProperty("DIACHI")
    private String diaChi;

    @JsonProperty("SDT")
    private String sdt;

    @JsonProperty("EMAIL")
    private String email;
}