package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheLoai {
    @JsonProperty("MATL")
    private String maTheLoai;

    @JsonProperty("TENTL")
    private String tenTheLoai;
}