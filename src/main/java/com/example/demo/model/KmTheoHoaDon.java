package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmTheoHoaDon extends ChuongTrinhKhuyenMai {

    private Double phantramkhuyenmai;
    @JsonProperty("SOTIENHOADON")
    private Double tongtien;
}
