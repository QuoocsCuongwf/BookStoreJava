package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChuongTrinhKhuyenMai {

    private String mactkm;
    private String tenchuongtrinh;
    private LocalDate ngaybd;

    private LocalDate ngaykt;

}