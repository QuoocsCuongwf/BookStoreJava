package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmTheoSanPham {
    @JsonProperty("MACTKM")
    private String mactkm;

    @JsonProperty("MASP")
    private String masp;

    @JsonProperty("PHANTRAMKHUYENMAI")
    private Double phantramkhuyenmai;

}