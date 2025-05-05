package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TacGia {
    @JsonProperty("MATG")
    private String matg;

    @JsonProperty("HOTG")
    private String hotg;

    @JsonProperty("TENTG")
    private String tentg;

    @JsonProperty("QUEQUAN")
    private String quequan;

    @JsonProperty("NAMSINH")
    private Integer namsinh;

    public TacGia(String matg, String hotg, String tentg, String quequan, Integer namsinh) {
        this.matg = matg;
        this.hotg = hotg;
        this.tentg = tentg;
        this.quequan = quequan;
        this.namsinh = namsinh;
    }

    public TacGia() {

    }
}

