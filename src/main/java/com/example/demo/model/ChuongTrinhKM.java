package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class ChuongTrinhKM {

    @JsonProperty("MACTKM")
    private String mactkm;
    @JsonProperty("PHANTRAMKHUYENMAI")
    private Double phantramkhuyenmai;
    @JsonProperty("NGAYBD")
    private LocalDate ngaybd;
    @JsonProperty("NGAYKT")
    private LocalDate ngaykt;
    public ChuongTrinhKM() {}
    public ChuongTrinhKM(String mactkm, Double phantramkhuyenmai, LocalDate ngaybd, LocalDate ngaykt) {
        this.mactkm = mactkm;
        this.phantramkhuyenmai=phantramkhuyenmai;
        this.ngaybd = ngaybd;
        this.ngaykt = ngaykt;
    }

}