package com.example.demo.databaseAccesssObject;


import com.example.demo.BUS.services.ChiTietPhieuNhapServices;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.ChiTietPhieuNhap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuNhapDAO {
    ConnectDatabase connectDatabase = new ConnectDatabase();
    List<ChiTietPhieuNhap> list = new ArrayList<>();

    public List<ChiTietPhieuNhap> getList() {
        String sql = "select * from chi_tiet_phieu_nhap";
        String json= connectDatabase.query(sql);
        ObjectMapper mapper = new ObjectMapper(); // c
        try {
            mapper.registerModule(new JavaTimeModule());
            list=mapper.readValue(json,  new TypeReference<List<ChiTietPhieuNhap>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public void addChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        String query ="INSERT INTO CHI_TIET_PHIEU_NHAP (MAPN,MASP,DONGIA,SL,THANHTIEN) VALUES ("
            +"'"+chiTietPhieuNhap.getMapn()+"','"+chiTietPhieuNhap.getMasp()+"',"
            +chiTietPhieuNhap.getDongia()+","+chiTietPhieuNhap.getSl()+","
            +chiTietPhieuNhap.getThanhtien()+")";
        try {
            connectDatabase.insert(query);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public void deleteChiTietPhieuNhap(String maPhieuNhap) {
        String query = "DELETE CHI_TIET_PHIEU_NHAP WHERE MAPN = '"+maPhieuNhap+"'";
        connectDatabase.update(query);
    }
    public void deleteChiTietPhieuNhap(String maPhieuNhap,String maSanPham) {
        String query = "DELETE CHI_TIET_PHIEU_NHAP WHERE MAPN = '"+maPhieuNhap+"' AND MASP = '"+maSanPham+"'";
        connectDatabase.update(query);
    }
    public void updateChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        String query ="UPDATE CHI_TIET_PHIEU_NHAP SET "+
                "DONGIA = "+chiTietPhieuNhap.getDongia()+", "+
                "SL = "+ chiTietPhieuNhap.getSl()+", "+
                "THANHTIEN = "+chiTietPhieuNhap.getThanhtien()+
                "WHERE MAPN = '"+chiTietPhieuNhap.getMapn()+"' AND MASP='"+chiTietPhieuNhap.getMasp()+"'";
    }
}
