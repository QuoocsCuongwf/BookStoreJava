package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmTheoSanPham extends ChuongTrinhKhuyenMai {
    private String masp;

    private Double phantramkhuyenmai;

}