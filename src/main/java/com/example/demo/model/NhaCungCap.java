package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhaCungCap {
    @JsonProperty("MANCC")
    private String maNhaCungCap;

    @JsonProperty("TENNCC")
    private String tenNhaCungCap;

    @JsonProperty("DIACHI")
    private String diaChi;

    @JsonProperty("SDT")
    private String sdt;

    @JsonProperty("EMAIL")
    private String email;


}
