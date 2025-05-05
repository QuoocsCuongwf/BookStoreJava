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
public class NhanVien {

    @JsonProperty("MANV")
    private String manv;

    @JsonProperty("HONV")
    private String honv;

    @JsonProperty("TENNV")
    private String tennv;

    @JsonProperty("CHUCVU")
    private String chucvu;

    @JsonProperty("CCCD")
    private String cccd;

    @JsonProperty("LUONG")
    private Integer luong;

    @JsonProperty("NGAYVAOLAM")
    private LocalDate ngayvaolam;

    @JsonProperty("MAIL")
    private String mail;

}