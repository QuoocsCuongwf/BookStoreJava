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

public class KhachHang {

    @JsonProperty("MAKH")
    private String makh;

    @JsonProperty("HOKH")
    private String hokh;

    @JsonProperty("TENKH")
    private String tenkh;

    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("SDT")
    private String sdt;

    @JsonProperty("DIACHI")
    private String diachi;


}