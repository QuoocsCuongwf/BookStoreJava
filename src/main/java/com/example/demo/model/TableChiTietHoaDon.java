package com.example.demo.model;

public class TableChiTietHoaDon {
    private int id;
    private String name;
    private int donGia;
    private int soLuong;
    private double khuyenMai;
    private int thanhTien;;

    public TableChiTietHoaDon(int id, String name, int donGia, int soLuong, double khuyenMai, int thanhTien) {
        this.id = id;
        this.name = name;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.khuyenMai = khuyenMai;
        this.thanhTien = thanhTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(Float khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
}
