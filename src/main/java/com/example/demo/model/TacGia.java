package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TacGia {
    @JsonProperty("MATG") private String matg;
    @JsonProperty("HOTG") private String hotg;
    @JsonProperty("TENTG") private String tentg;
    @JsonProperty("QUEQUAN") private String quequan;
    @JsonProperty("NAMSINH") private Integer namsinh;

    // Getters and setters
    public String getMatg() { return matg; }
    public void setMatg(String matg) { this.matg = matg; }
    public String getHotg() { return hotg; }
    public void setHotg(String hotg) { this.hotg = hotg; }
    public String getTentg() { return tentg; }
    public void setTentg(String tentg) { this.tentg = tentg; }
    public String getQuequan() { return quequan; }
    public void setQuequan(String quequan) { this.quequan = quequan; }
    public Integer getNamsinh() { return namsinh; }
    public void setNamsinh(Integer namsinh) { this.namsinh = namsinh; }
}