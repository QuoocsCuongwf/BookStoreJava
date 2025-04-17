package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhaXuatBan {
    @JsonProperty("MANXB")
    private String manxb;

    @JsonProperty("TENNXB")
    private String tennxb;

    @JsonProperty("DIACHI")
    private String diachi;

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